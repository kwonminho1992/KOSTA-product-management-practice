package com.my.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;

/**
 * repository in file type
 * all method name is same with ProductListRepository
 * txt파일로 생성
 * @author asus
 *
 */
//ProductListRepository와 메서드 이름을 같게 하여, 사용자의 편의성을 높임 (사용자는 처음 객체를 선언할때의 데이터타입만 건드리면 됨)
public class ProductFileRepository implements ProductRepository {
	//field
	private String fileName = "products.txt";
//	try {
//		private Scanner scanner = new Scanner(System.in); 
//	} catch (Exception e) {
//		private Scanner scanner = new Scanner(System.in); 
//	}	
//	멤버변수로 scanner같은 외부자원을 사용할 경우, 어떤 이유로든 scanner의 연결이 한번 끊어지면 해당 자원은 더 이상 사용할 수 없게 됨 (예외처리를 통해 새로 객체 생성)
//	예상치 못한 문제가 생길 경우, 멤버변수를 사용할 수 없게 되므로 안정적이지 않은 방법임.
//	추천 방법 : 메서드 내에서 로컬변수로 선언
	//constructor
	public ProductFileRepository() {	
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void insert(Product product) throws AddException {
		// 해당 상품의 상품번호, 상품명, 상품가격 정보를 변수에 담기
		String productNo = product.getProductNo();
		String productName = product.getProductName();
		int productPrice = product.getProductPrice();

		// 파일에 상품 쓰기
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName, true); // append를 true로 하면 파일이 있을 시 내용추가, 없을 시에는 파일 생성
			fw.write("\n" + productNo + ":" + productName + ":" + productPrice);
		} catch (IOException e) {
			// No exception message
		} finally { // 파일을 닫기
			if (fw != null) { // if문에서는 블럭처리를 꼭 하는게 오타로 인해 코드가 망가지지 않게 하는 안전한 방법이다
				// NullPointerException에 걸리지 않게 조치
				try {
					//fw.flush() // close 할때 자동 flush되므로, 1줄분량만 파일에 쓰는 이 메서드에선 굳이 불필요
					fw.close();
				} catch (IOException e) {
					// No exception message
				}
			}
		}		
	}	

	public void modify(String modifiedProductNo, String modifiedProductName, int modifiedProductPrice) throws AddException{
		try { // modify productName & productPrice
			Product product = selectByProductNo(modifiedProductNo);
			product.setProductName(modifiedProductName);
			product.setProductPrice(modifiedProductPrice);
		} catch (FindException e) { // print exception message by println() method
			System.out.println("FindException : There is no matched productNo in repository.");
		}		
	}
	

	public List<Product> selectAll() throws FindException {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(fileName));
			List<Product> all = new ArrayList<Product>();
			while (scanner.hasNextLine() == true) { // 읽어올 줄이 있다면 while loop 반복
				String line = scanner.nextLine();
				String[] arr = line.split(":", 3);
				String productNo = arr[0];
				String productName = arr[1];
				int productPrice = Integer.parseInt(arr[2]);
				Product product = new Product(productNo, productName, productPrice);
				all.add(product);
			}
			if (all.size() == 0) { // 예외발생 : 파일은 있으나 파일 안에 저장된 상품이 하나도 없음
				throw new FindException("FindException : There is no product in repository.");				
			}
			return all;
		} catch (FileNotFoundException e) { // 파일이 없음 == 상품이 하나도 없음
			throw new FindException("FindException : There is no product in repository.");
		} finally { // 파일을 닫기 (* return을 수행하기 직전에 finally 구문이 먼저 실행됨)
			if (scanner != null) { // NullPointerException에 걸리지 않게 조치
				scanner.close(); 
			}
		}
	}


	public Product selectByProductNo(String productNoInConsole) throws FindException {		
		Scanner scanner = null;
		try {
			Product product = null;
			scanner = new Scanner(new FileInputStream(fileName));
			while (scanner.hasNextLine() == true) { // 읽어올 줄이 있다면 while loop 반복
				String line = scanner.nextLine();
				String[] arr = line.split(":", 3);
				String productNo = arr[0];
				String productName = arr[1];
				int productPrice = Integer.parseInt(arr[2]);	
				if (productNo.equalsIgnoreCase(productNoInConsole)) { //읽어온 줄의 productNo가 일치하면 해당 상품의 정보를 가진 product객체 생성	
					product = new Product(productNo, productName, productPrice);
					return product;
				}
			}
			 // 예외발생 : 파일안에 상품이 하나도 업음 or 상품번호가 일치하는 상품이 없음
			throw new FindException("FindException : Cannot find matched productNo in repository.");				
		} catch (FileNotFoundException e) { // 파일이 없음 (== 상품번호가 일치하는 상품이 없음)
			throw new FindException("FindException : Cannot find matched productNo in repository.");	
		} finally { // 파일을 닫기 (* return을 수행하기 직전에 finally 구문이 먼저 실행됨)
			if (scanner != null) { // NullPointerException에 걸리지 않게 조치
				scanner.close(); 
			}
		}
	}
	
	public List<Product> selectByProductNoOrName(String keyword) throws FindException {		
		//ProductNo or productName에 키워드가 포함되어있는 상품을 모두 반환
		Scanner scanner = null;
		try {
			List<Product> allSelected = new ArrayList<Product>();
			scanner = new Scanner(new FileInputStream(fileName));
			while (scanner.hasNextLine() == true) { // 읽어올 줄이 있다면 while loop 반복
				String line = scanner.nextLine();
				String[] arr = line.split(":", 3);
				String productNo = arr[0];
				String productName = arr[1];
				int productPrice = Integer.parseInt(arr[2]);	
				//읽어온 줄의 상품번호나 상품명에 keyword가 있다면 해당 상품정보로 product 객체를 만든 후, List에 add
				if (productName.contains(keyword.toUpperCase()) || productName.contains(keyword)) { 	
					allSelected.add(new Product(productNo, productName, productPrice));				
				}
			}
			if (allSelected.size() == 0) { // 예외발생 : 파일안에 저장된 상품이 없음 or 검색어와 일치하는 상품이 없음
				throw new FindException("FindException : Cannot find matched products with <" + keyword + "> in repository.");					
			}
			return allSelected;
		} catch (FileNotFoundException e) { // 파일이 없음 (== 검색어와 일치하는 상품이 없음)
			throw new FindException("FindException : Cannot find matched products with <" + keyword + "> in repository.");	
		} finally { // 파일을 닫기 (* return을 수행하기 직전에 finally 구문이 먼저 실행됨)
			if (scanner != null) { // NullPointerException에 걸리지 않게 조치
				scanner.close(); 
			}
		}
	}
	
}

