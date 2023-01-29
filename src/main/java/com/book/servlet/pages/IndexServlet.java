package com.book.servlet.pages;

import com.book.entity.User;
import com.book.service.BookService;
import com.book.service.impl.BookServiceImpl;
import com.book.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("borrow_list", bookService.getBorrowList());
        context.setVariable("active_book_list", bookService.getActiveBookList());
        context.setVariable("book_total", bookService.getBookTotal());
        context.setVariable("student_total", bookService.getStudentTotal());
        ThymeleafUtil.process("index.html", context, resp.getWriter());
    }
}
