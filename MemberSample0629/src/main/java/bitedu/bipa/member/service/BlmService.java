package bitedu.bipa.member.service;

import java.util.ArrayList;

import bitedu.bipa.member.dao.BlmDAO;
import bitedu.bipa.member.vo.BookCopy;

public class BlmService {
	private BlmDAO dao;
	public BlmService() {
		dao = new BlmDAO();
	}
	public boolean registBook(BookCopy copy) {
		boolean flag = false;
		flag = dao.insertBook(copy);
		return flag;
	}
	
	public ArrayList<BookCopy> searchBookAll(){
		ArrayList<BookCopy> list = null;
		list = dao.selectBookAll();
		return list;
	}
	public boolean removeBook(String bookSeq) {
		// TODO Auto-generated method stub
		boolean flag = false;
		flag = dao.deleteBook(Integer.parseInt(bookSeq));
		return flag;
	}
	
	public boolean modifyBook(BookCopy copy) {
		boolean flag = false;
		flag = dao.updateBook(copy);
		System.out.println("modify"+copy);
		return flag;
	}
}
