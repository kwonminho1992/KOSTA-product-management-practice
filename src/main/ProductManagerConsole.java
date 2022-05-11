package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.OptException;
import com.my.repository.ProductFileRepository;
import com.my.repository.ProductListRepository;

public class ProductManagerConsole {

	//private Product product = new Product();
	
	/**
	 * default constructor
	 */
	public ProductManagerConsole() {
	}

	/**
	 * show user the menu and user can choose which work will proceed.
	 * 1-상품등록, 2-상품전체조회, 3-상품번호로조회, 4-검색어로조회, 5-상품수정, 9-종료
	 * @return String opt
	 * @throws OptException
	 */
	public String optConsole () throws OptException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("작업구분 : 1-상품등록, 2-상품전체조회, 3-상품번호로조회, 4-검색어로조회, 5-상품수정, 9-종료");
		String opt = scanner.nextLine();
		if (opt.equals("1") || opt.equals("2") || opt.equals("3") ||
				opt.equals("4") || opt.equals("5") || opt.equals("9")) { //right input, then return opt
			return opt;	
		}
		throw new OptException("OptException : Wrong choice. You should input 1, 2, 3, 4, 5, 9 only"); // wrong input, occur exception
	}
	
	/**
	 * add product into repository
	 * @param ProductListRepository repository
	 * @throws AddException
	 */
	public void add () throws AddException {
		Scanner scanner = new Scanner(System.in);
		ProductFileRepository repository = new ProductFileRepository();
		System.out.println(">>상품등록<<");
		String productNo = "";
		String productName = "";
		String productPriceByString = "";
		int productPrice = 0;
		System.out.println("상품번호를 입력하세요 (F - food, G - goods, D - drink) : ");
		productNo = scanner.nextLine().toUpperCase();
		System.out.println("상품명을 입력하세요 : ");
		productName = scanner.nextLine();
		System.out.println("상품의 가격을 입력하세요 : ");
		productPriceByString = scanner.nextLine();
		try { //if user's input is not integer
			productPrice = Integer.parseInt(productPriceByString);
		} catch (NumberFormatException e) {
			throw new AddException("AddException : You should input integer only into productPrice");
		}
		assessInputRule(productNo, productName, productPrice); // assess user kept the input rule or not
//		System.out.println("상품정보를 입력하세요 : ");
//		String productInfo = scanner.nextLine();
//		System.out.println("상품제조일자를 입력하세요 (ex. 2022/05/03) : ");
//		String productMfdBystr = scanner.nextLine(); // type change String -> Date
//		Object procuctMfdByStr = (Object) productMfdBystr; // type change String -> Date
//		Date productMfd = (Date) procuctMfdByStr; // type change String -> Date
		repository.insert(new Product(productNo, productName, productPrice)); 
		System.out.println("Successfully added!");
		System.out.println(new Product(productNo, productName, productPrice)); //call toString() method in Product class
	}
	
	/**
	 * find all products in repository and then print them
	 * @param ProductListRepository repository
	 * @throws FindException
	 */
	public void findAll () throws FindException {
		System.out.println(">>상품 전체조회<<");
		ProductFileRepository repository = new ProductFileRepository();
		List<Product> list = repository.selectAll(); // select all products in repository
		for (int i = 0; i < list.size(); i++) { // print them
			Product p = list.get(i);
			System.out.println(p); // p.toString() method 자동 호출 - 상품번호, 상품명, 가격 출력
			// toString method가 자동호출되는 메커니즘 : println method에 대한 공식문서 설명을 볼것
			// (Object type의 경우 valueOf() or toString() method를 호출시킴)
		}
	}
	
	/**
	 * find the product information by user's input(full productNo)
	 * @param ProductListRepository repository
	 * @throws FindException
	 */
	public void findByProductNo() throws FindException {
		Scanner scanner = new Scanner(System.in);
		ProductFileRepository repository = new ProductFileRepository();
		System.out.println(">>상품번호로 조회<<");
		String keyword = scanner.nextLine().toUpperCase();
		Product p = repository.selectByProductNo(keyword); // select a product has keyword
		System.out.println(p);
	}
	
	/**
	 * find the products by keyword (if matches to productNo or productName, then print)
	 * @param ProductListRepository repository
	 * @throws FindException
	 */
	public void findByProductNoOrName () throws FindException {
		Scanner scanner = new Scanner(System.in);
		ProductFileRepository repository = new ProductFileRepository();
		System.out.println(">>상품번호나 이름으로 조회<<");
		String keyword = scanner.nextLine();
		List<Product> list = repository.selectByProductNoOrName(keyword);
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProductNo().contains(keyword.toUpperCase()) || 
					list.get(i).getProductName().contains(keyword)) { // there is a product meets keyword
				System.out.println(list.get(i)); // print the product
				count++;
			}
		}
		if (count != 0) {
			System.out.println(count + " products searched");
		} else {
			System.out.println("There is no product. Try search again with other keyword");
		}
	}	
	
	/**
	 * Modify the product
	 * @param ProductListRepository repository
	 * @throws FindException
	 * @throws AddException
	 */
	public void modify () throws FindException, AddException {
		Scanner scanner = new Scanner(System.in);
		ProductFileRepository repository = new ProductFileRepository();
		System.out.println(">>상품 수정<<");	
		System.out.println("수정할 상품의 상품번호를 입력하세요 : ");
		String productNo = scanner.nextLine().toUpperCase(); 
		Product product = repository.selectByProductNo(productNo); // select product will be modified
		
		//modify
		String productName = "";
		String productPriceByString = "";
		System.out.println("현재 상품명 : [" + product.getProductName() + "]. 변경안하려면 enter를 누르세요 : ");
		productName = scanner.nextLine();
		System.out.println("현재 가격 : [" + product.getProductPrice() + "]. 변경안하려면 enter를 누르세요 : ");
		productPriceByString = scanner.nextLine();
		int productPrice;
		// if user doesn't want change
		if (productName.equals("")) {
			productName = product.getProductName();
		}
		if (productPriceByString.equals("")) {
			productPrice = product.getProductPrice();
		} else {
			try { //if user's input is not integer
				productPrice = Integer.parseInt(productPriceByString);
			} catch (NumberFormatException e) {
				throw new AddException("AddException : You should input integer only into productPrice");
			}
		}
		assessInputRule(productNo, productName, productPrice); // assess user kept the input rule or not
		try {
			repository.modify(product, productName, productPrice);
			System.out.println("Successfully modified!");
			System.out.println(product); //call toString() method in Product class
		} catch (AddException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// input assess when add or modify product
	private void assessInputRule (String productNo, String productName, int productPrice) throws AddException{
		int i = 0;
		if (productNo.equals("") || productName.equals("")) { // 상품번호, 상품명은 빈칸일 수 없음
			throw new AddException("AddException : productNo and productName should not be blank");
		} else if (!((int) productNo.charAt(i) == 68) && !((int) productNo.charAt(i) == 70) && !((int) productNo.charAt(i) == 71)) { // 상품번호는 D, F or G로 시작해야함
			throw new AddException("AddException : productNo should start with D, F or G");
		} else if (productPrice <= 0) { //상품가격은 0이나 음수가 되면 안됨
			throw new AddException("AddException : productPrice should be at least 1. Not allowed 0 and minus");
		} else { //입력규칙 검사를 통과하면 중복여부 검사 진행
			//중복여부 검사
			Scanner scanner = null;
			boolean isDuplication = false; // 중복여부를 체크하는 변수
			ProductFileRepository repository = new ProductFileRepository();
			try {
				Product productForAssess = null;
				scanner = new Scanner(new FileInputStream(repository.getFileName()));
				while (scanner.hasNextLine() == true) { // 읽어올 줄이 있다면 while loop 반복
					String line = scanner.nextLine();
					String[] arr = line.split(":", 3);
					String productNoInFile = arr[0];
					productName = arr[1];
					productPrice = Integer.parseInt(arr[2]);	
					if (productNoInFile.equals(productNo)) { //productNo가 중복되면 반복문종료
						isDuplication = true; // 중복발견으로 인해 값을 true로 변경
						break;
					}
				}
			} catch (FileNotFoundException e) { // 파일이 없음 -> 파일을 새로 만들면 되므로 예외발생 X
			
			} finally { // 파일을 닫기 (* return을 수행하기 직전에 finally 구문이 먼저 실행됨)
				if (scanner != null) { // NullPointerException에 걸리지 않게 조치
					scanner.close(); 
				}
			}		
			
			if (isDuplication == true) { // 중복이 있는 경우
				throw new AddException("AddException : You can't deposit "
						+ "[productNo='" + productNo + "']. the product already exists.");
			} // 중복이 업는 경우 검사 최종 통과 (예외처리에 걸리지 않고, 메서드를 무사히 통과)
		}
	}
}

