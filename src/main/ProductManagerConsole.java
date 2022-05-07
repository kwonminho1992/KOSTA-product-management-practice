package main;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.OptException;
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
	 * @return user's opt
	 */
	public String optConsole () throws OptException {
		System.out.println("작업구분 : 1-상품등록, 2-상품전체조회, 3-상품번호로조회, 4-검색어로조회, 5-상품수정, 9-종료");
		Scanner scanner = new Scanner(System.in);
		String opt = scanner.nextLine();
		if (opt.equals("1") || opt.equals("2") || opt.equals("3") ||
				opt.equals("4") || opt.equals("5") || opt.equals("9")) { //right input, then return opt
			return opt;	
		}
		throw new OptException("Wrong choice. You should input 1, 2, 3, 4, 5, 9 only"); // wrong input, occur exception
	}
	
	public void add (ProductListRepository repository) throws AddException {
		Scanner scanner = new Scanner(System.in);
		System.out.println(">>상품등록<<");
		String productNo = "";
		String productName = "";
		int productPrice = 0;
		try {
			System.out.println("상품번호를 입력하세요 (F - food, G - goods, D - drink) : ");
			productNo = scanner.nextLine().toUpperCase();
			System.out.println("상품명을 입력하세요 : ");
			productName = scanner.nextLine();
			System.out.println("상품가격를 입력하세요 : ");
			productPrice = scanner.nextInt();
		} catch (InputMismatchException e) {
			throw new AddException("AddException : You should input integer only into productPrice");
		}
//		System.out.println("상품정보를 입력하세요 : ");
//		String productInfo = scanner.nextLine();
//		System.out.println("상품제조일자를 입력하세요 (ex. 2022/05/03) : ");
//		String productMfdBystr = scanner.nextLine(); // type change String -> Date
//		Object procuctMfdByStr = (Object) productMfdBystr; // type change String -> Date
//		Date productMfd = (Date) procuctMfdByStr; // type change String -> Date
		repository.insert(new Product(productNo, productName, productPrice));
	}
	
	public void findAll (ProductListRepository repository) throws FindException {
		System.out.println(">>상품 전체조회<<");
		List<Product> list = repository.selectAll();
		for (int i = 0; i < list.size(); i++) {
			Product p = list.get(i);
			System.out.println(p); // p.toString() method 자동 호출 - 상품번호, 상품명, 가격 출력
			// toString method가 자동호출되는 메커니즘 : println method에 대한 공식문서 설명을 볼것
			// (Object type의 경우 valueOf() or toString() method를 호출시킴)
		}
	}
	
	public void findByProductNo(ProductListRepository repository) throws FindException {
		Scanner scanner = new Scanner(System.in);
		System.out.println(">>상품번호로 조회<<");
		String keyword = scanner.nextLine().toUpperCase();
		List<Product> list = repository.selectAll();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProductNo().contains(keyword)) { // there is a product meets keyword
				System.out.println(list.get(i)); // print the product
				count++;
			}
		}
		if (count != 0) {
			System.out.println(count + " products searched");
		} else {
			System.out.println("There is no result. Try search again with other keyword");
		}

	}
	
	public void findByProductNoOrName (ProductListRepository repository) throws FindException {
		Scanner scanner = new Scanner(System.in);
		System.out.println(">>상품번호나 이름으로 조회<<");
		String keyword = scanner.nextLine();
		List<Product> list = repository.selectAll();
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
	
	public void modify (ProductListRepository repository) throws FindException, AddException {
		Scanner scanner = new Scanner(System.in);
		System.out.println(">>상품 수정<<");	
		System.out.println("수정할 상품의 상품번호를 입력하세요 : ");
		String productNo = scanner.nextLine().toUpperCase(); 
		List<Product> list = repository.selectAll();
		Product product = new Product();
		int i = 0;
		for (; i < list.size(); i++) {
			if (list.get(i).getProductNo().equals(productNo)) { // find the product will be modified
				product = list.get(i);
				break;
			}
		}
		if (i == list.size()) { // throw FindException if there is no productNo 
			throw new FindException("FindException : There is no productNo. Try search again with other keyword");
		}
		
		//modify
		String productName = "";
		String productPriceByString = "";
		System.out.println("현재 상품번호 : [" + product.getProductNo() + "]. 변경안하려면 enter를 누르세요 : ");
		productNo = scanner.nextLine().toUpperCase(); 
		System.out.println("현재 상품명 : [" + product.getProductName() + "]. 변경안하려면 enter를 누르세요 : ");
		productName = scanner.nextLine();
		System.out.println("현재 가격 : [" + product.getProductPrice() + "]. 변경안하려면 enter를 누르세요 : ");
		productPriceByString = scanner.nextLine();
		int productPrice;
		// if user doesn't want change
		if (productNo.equals("")) {
			productNo = product.getProductNo();
		}
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
		try {
			if (productNo.equals(product.getProductNo())) {
				repository.modify(product, productName, productPrice);
			} else {
				repository.modify(product, productNo, productName, productPrice);
			}
			System.out.println("Successfully modified!");
		} catch (AddException e) {
			System.out.println(e.getMessage());
		}
	}

}
