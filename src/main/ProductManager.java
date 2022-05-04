package main;

import java.util.List;
import java.util.Scanner;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.ProductListRepository;

/**
 * add, revise, inquire, delete products in repository
 * @author kwonminho
 *
 */
public class ProductManager {
	/**
	 * 작업구분 : 1-상품등록, 2-상품전체조회, 3-상품번호로조회, 4-검색어로조회, 5-상품수정, 9-종료
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		ProductListRepository repository = new ProductListRepository();
		ProductManagerConsole managerconsole = new ProductManagerConsole();
		System.out.println("작업구분 : 1-상품등록, 2-상품전체조회, 3-상품번호로조회, 4-검색어로조회, 5-상품수정, 9-종료");
		
		while (true) {
			String opt = scanner.nextLine();
			switch(opt) {
			case "1" :
				managerconsole.add();
			case "2" :
				managerconsole.findAll();
			case "3" :
				managerconsole.findAll();
			case "4" :
				managerconsole.findAll();
			case "5" :
				managerconsole.findAll();
			case "9" :
				managerconsole.findAll();
			}
		
		}
		
//		Product p1 = new Product("D0001", "아메리카노1", 1000);
//		Product p2 = new Product("D0002", "아메리카노2", 1000);
//		Product p3 = new Product("D0002", "아메리카노3", 1000);// print - You can't deposit [productNo='D0002']. the product already exists..
//		Product p4 = new Product("D0004", "아메리카노4", 4000); 
//		try { // 한꺼번에 예외처리를 할 경우, p4가 insert될 기회도 없이 catch문으로 넘어감. 따라서 일일히 예외처리함
//			repository.insert(p1);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//		try {
//			repository.insert(p2);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//		try {
//			repository.insert(p3);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//		try {
//			repository.insert(p4);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			List<Product> list = repository.selectAll();	
//			for (int i = 0; i < list.size(); i++) {
//				Product p = list.get(i);
//				System.out.println(p); //p.toString() method 자동 호출 - 상품번호, 상품명, 가격 출력
//				//toString method가 자동호출되는 메커니즘 : println method에 대한 공식문서 설명을 볼것 
//				//(Object type의 경우 valueOf() or toString() method를 호출시킴)
//			}
//		} catch (FindException e) {
//			System.out.println(e.getMessage()); // Exception message : There is no product in repository.
//			e.printStackTrace();
//		}
		
		try {
			Product product = repository.selectByProductNo("D0001"); // 상품정보 return
			System.out.println(product); // 상품정보 출력
		} catch (FindException e) {
			System.out.println(e.getMessage());
		}

	}

}
