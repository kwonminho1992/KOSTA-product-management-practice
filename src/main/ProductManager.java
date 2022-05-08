package main;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.OptException;
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
		ProductListRepository repository = new ProductListRepository();
		ProductManagerConsole managerConsole = new ProductManagerConsole();
		String opt;
		try {
			repository.insert(new Product("F0001", "아메리카노1", 1000));
			repository.insert(new Product("F0002", "아메리카노1", 1000));
			repository.insert(new Product("D0002", "아메리카노1", 1000));
		} catch (AddException e) {
		}
		while (true) {
			try {
				opt = managerConsole.optConsole(); //get user's opt
			} catch (OptException e) { //if user's input is wrong, catch OptException
				System.out.println(e.getMessage());
				continue;
			}	
			switch(opt) {
				case "1": 
					try {
						managerConsole.add(repository);
					} catch (AddException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "2":
					try {
						managerConsole.findAll(repository);
					} catch (FindException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "3":
					try {
						managerConsole.findByProductNo(repository);
					} catch (FindException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "4":
					try {
						managerConsole.findByProductNoOrName(repository);
					} catch (FindException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "5":
					try {
						managerConsole.modify(repository);
					} catch (FindException e) {
						System.out.println(e.getMessage());
					} catch (AddException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "9":
					System.exit(0);

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
//			Product product = repository.selectByProductNo("D0001"); // 상품정보 return
//			System.out.println(product); // 상품정보 출력
//		} catch (FindException e) {
//			System.out.println(e.getMessage());
//		}

		

	}
}


