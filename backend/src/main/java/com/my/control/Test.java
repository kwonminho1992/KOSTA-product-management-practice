package com.my.control;

import com.my.exception.FindException;
import com.my.repository.OrderOracleRepository;
import com.my.repository.OrderRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.sql.MyConnection;

public class Test {

	public static void main(String[] args) {
		OrderRepository repository = new OrderOracleRepository();
		System.out.println("repository생성");
		try {
			List<OrderInfo> list = repository.selectById("asd");
			System.out.println(list.toString());
			
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
