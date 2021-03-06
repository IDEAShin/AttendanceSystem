package com.ideashin.attendance.controller;

import com.alibaba.fastjson.JSON;
import com.ideashin.attendance.entity.Note;
import com.ideashin.attendance.service.NoteService;
import com.ideashin.attendance.service.impl.NoteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Shin
 * @Date: 2019/7/12 11:01
 * @Blog: ideashin.com
 */

public class NoteController extends HttpServlet {
    private NoteService noteService = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String opt = req.getAttribute("opt").toString();

        switch (opt) {
            case "findAllNotes":
                findAllNotes(req, resp);
                break;
            case "removeOneNote":
                removeOneNote(req, resp);
                break;
            case "addOneNote":
                addOneNote(req, resp);
                break;
            case "editOneNote":
                editOneNote(req, resp);
                break;
            case "findSomeNotes":
                findSomeNotes(req, resp);
                break;
            case "findNoteToAttendance":
                findNoteToAttendance(req, resp);
                break;
            default:
        }
    }

    /**
     * 查询所有的请假单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAllNotes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.valueOf(req.getParameter("page"));
        int rows = Integer.valueOf(req.getParameter("rows"));
        int total = noteService.getCount();

        List<Note> list = noteService.findAll(page, rows);
        HashMap<String, Object> map = new HashMap<>(2);

        map.put("total", total);
        map.put("rows", list);

        String jsonString = JSON.toJSONString(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
        out.close();
    }

    /**
     * 条件查询
     * @param req
     * @param resp
     * @throws IOException
     */
    public void findSomeNotes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer noteTypeSearch = Integer.valueOf(req.getParameter("noteTypeSearch"));
        Integer deptSelect = null;
        if (!"全部".equals(req.getParameter("deptSelect"))) {
            deptSelect = Integer.valueOf(req.getParameter("deptSelect"));
        }
        String empSearch = req.getParameter("empSearch");
        String dateSearchS = req.getParameter("dateSearch");

        Date dateSearch = null;
        if (dateSearchS != null && !dateSearchS.equals("")) {
            try {
                dateSearch = new SimpleDateFormat("yyyy-MM-dd").parse(dateSearchS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<Note> list = noteService.findSome(noteTypeSearch, deptSelect, empSearch, dateSearch);
        HashMap<String, Object> map = new HashMap<>(2);

        map.put("total", list.size());
        map.put("rows", list);

        String jsonString = JSON.toJSONString(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
        out.close();
    }

    public void findNoteToAttendance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer employeeID = Integer.valueOf(req.getParameter("employeeID"));
        Date attendanceDate = null;
        try {
            attendanceDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("attendanceDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------");
        System.out.println(employeeID);
        System.out.println(attendanceDate);
        System.out.println("--------------------");
        List<Note> list = noteService.findNoteToAttendance(employeeID, attendanceDate);
        HashMap<String, Object> map = new HashMap<>(2);

        map.put("total", list.size());
        map.put("rows", list);

        String jsonString = JSON.toJSONString(map);
        PrintWriter out = resp.getWriter();
        System.out.println("json===================="+ jsonString);
        out.print(jsonString);
        out.flush();
        out.close();
    }

    /**
     * 删除单条
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void removeOneNote(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer noteID = Integer.valueOf(req.getParameter("noteID"));
        Boolean data = noteService.removeOne(noteID);

        PrintWriter out = resp.getWriter();
        out.print(data);
        out.flush();
        out.close();
    }

    /**
     * 插入单条数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addOneNote(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer employeeID = Integer.valueOf(req.getParameter("employeeID"));
        Integer departmentID = Integer.valueOf(req.getParameter("departmentID"));
        Integer operatorID = null;
        if (!"".equals(req.getParameter("operatorID")) && req.getParameter("operatorID") != null) {
            operatorID = Integer.valueOf(req.getParameter("operatorID"));
        }
        Integer noteTypeID = Integer.valueOf(req.getParameter("noteTypeID"));
        String  fillInTimeS = req.getParameter("fillInTime");
        String cause = req.getParameter("cause");
        String startDateS = req.getParameter("startDate");
        String startTime = req.getParameter("startTime");
        String endDateS = req.getParameter("endDate");
        String endTime = req.getParameter("endTime");
        String directorSign = req.getParameter("directorSign");
        String administrationSign = req.getParameter("administrationSign");
        String presidentSign = req.getParameter("presidentSign");

//        Integer adminID = Integer.valueOf(req.getParameter("adminID"));
//        String noteMemo = req.getParameter("noteMemo");
//        String isVerify = req.getParameter("isVerify");
        Date fillInTime = null;
        Date startDate = null;
        Date endDate = null;
        try {
            fillInTime = new SimpleDateFormat("yyyy-MM-dd").parse(fillInTimeS);
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateS);
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Note note = new Note();
        note.setEmployeeID(employeeID);
        note.setDepartmentID(departmentID);
        note.setOperatorID(operatorID);
        note.setNoteTypeID(noteTypeID);
        note.setFillInTime(fillInTime);
        note .setCause(cause);
        note.setStartDate(startDate);
        note.setStartTime(startTime);
        note.setEndDate(endDate);
        note.setEndTime(endTime);
        note.setDirectorSign(directorSign);
        note.setAdministrationSign(administrationSign);
        note.setPresidentSign(presidentSign);

        note.setAdminID(2);
        note.setNoteMemo("");
        note.setIsVerify("1");


        Boolean data = noteService.addOne(note);

        PrintWriter out = resp.getWriter();
        out.print(data);
        out.flush();
        out.close();
    }

    /**
     * 修改单条数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void editOneNote(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer noteID = Integer.valueOf(req.getParameter("noteID"));
        Integer employeeID = Integer.valueOf(req.getParameter("employeeID"));
        Integer departmentID = Integer.valueOf(req.getParameter("departmentID"));
        Integer operatorID = null;
        if (!"".equals(req.getParameter("operatorID")) && req.getParameter("operatorID") != null) {
            operatorID = Integer.valueOf(req.getParameter("operatorID"));
        }
        Integer noteTypeID = Integer.valueOf(req.getParameter("noteTypeID"));
        String  fillInTimeS = req.getParameter("fillInTime");
        String cause = req.getParameter("cause");
        String startDateS = req.getParameter("startDate");
        String startTime = req.getParameter("startTime");
        String endDateS = req.getParameter("endDate");
        String endTime = req.getParameter("endTime");
        String directorSign = req.getParameter("directorSign");
        String administrationSign = req.getParameter("administrationSign");
        String presidentSign = req.getParameter("presidentSign");

//        Integer adminID = Integer.valueOf(req.getParameter("adminID"));
//        String noteMemo = req.getParameter("noteMemo");
//        String isVerify = req.getParameter("isVerify");

        Date fillInTime = null;
        Date startDate = null;
        Date endDate = null;
        try {
            fillInTime = new SimpleDateFormat("yyyy-MM-dd").parse(fillInTimeS);
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateS);
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Note note = new Note();
        note.setNoteID(noteID);
        note.setEmployeeID(employeeID);
        note.setDepartmentID(departmentID);
        note.setOperatorID(operatorID);
        note.setNoteTypeID(noteTypeID);
        note.setFillInTime(fillInTime);
        note.setCause(cause);
        note.setStartDate(startDate);
        note.setStartTime(startTime);
        note.setEndDate(endDate);
        note.setEndTime(endTime);
        note.setDirectorSign(directorSign);
        note.setAdministrationSign(administrationSign);
        note.setPresidentSign(presidentSign);

        note.setAdminID(2);
        note.setNoteMemo("");
        note.setIsVerify("1");

        Boolean data = noteService.editOne(note);

        PrintWriter out = resp.getWriter();
        out.print(data);
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        super.destroy();
        noteService = null;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        noteService = new NoteServiceImpl();
    }

}
