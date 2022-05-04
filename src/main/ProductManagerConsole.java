import java.util.List;
import java.util.Scanner;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.repository.ProductListRepository;

public class ProductManagerConsole {
	//TODO완성하세요
	//ProductListRepository도 완성하세요
	public static void main(String[] args) {
		ProductManagerConsole managerConsole = new ProductManagerConsole();
		
		while(true) {
			System.out.println("작업구분 :상품등록-1, 상품전체조회-2, 상품번호로 조회-3, 검색어로 조회-4, 상품수정-5, 종료-9");

		
			String opt = sc.nextLine();
			switch(opt) {
			case "1": 
				managerConsole.add();
				break;
			case "2":
				managerConsole.findAll();
				break;
			case "3":
				managerConsole.findByProdNo();
				break;
			case "4":
				managerConsole.findByProdNoOrName();
				break;
			case "5":
				managerConsole.modify();
				break;
			case "9":
				System.exit(0);
			}
		}
	}

}
