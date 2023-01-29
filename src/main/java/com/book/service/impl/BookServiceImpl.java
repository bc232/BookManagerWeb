package com.book.service.impl;

import com.book.dao.BookMapper;
import com.book.dao.StudentMapper;
import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;
import com.book.service.BookService;
import com.book.util.MybatisUntil;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    @Override
    public List<Borrow> getBorrowList() {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            List<Borrow> borrowList = mapper.getBorrowList();
            return borrowList;
        }
    }

    @Override
    public void returnBook(String id) {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.deleteBorrow(id);
        }
    }


    @Override
    public List<Book> getActiveBookList() {
        Set<Integer> set = new HashSet<>();
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            return mapper.getBookList()
                    .stream()
                    .filter(book -> !set.contains(book.getBid()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Student> getStudentList() {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            return mapper.getStudentList();
        }
    }

    @Override
    public void addBorrow(int sid, int bid) {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.addBorrow(sid, bid);
        }
    }


    @Override
    public Map<Book, Boolean> getBookList() {
        Set<Integer> set = new HashSet<>();
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            Map<Book, Boolean> resultMap = new LinkedHashMap<>();
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.getBookList().forEach(book -> {
                resultMap.put(book, set.contains(book.getBid()));
            });
            return resultMap;
        }
    }


    @Override
    public void deleteBook(int bid) {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.deleteBook(bid);
        }
    }

    @Override
    public void addBook(String title, String desc, double price) {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.addBook(title, desc, price);
        }
    }


    @Override
    public int getBookTotal() {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            return mapper.getBookTotal();
        }
    }

    @Override
    public int getStudentTotal() {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            return mapper.getStudentTotal();
        }
    }
}
