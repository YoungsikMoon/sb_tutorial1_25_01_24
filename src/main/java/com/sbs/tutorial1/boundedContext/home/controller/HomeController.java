package com.sbs.tutorial1.boundedContext.home.controller;

import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@RequestMapping("/home")
@Controller
public class HomeController {
    int num;
    List<Person> personList;

    @Autowired
    private MemberService memberService;

    public HomeController(){
        num=-1;
        personList = new ArrayList<>();

    }

    @GetMapping("/main1")
    @ResponseBody
    public String showHome() {
        return "어서오세요.";
    }

    @GetMapping("/main2")
    @ResponseBody
    public String showHome2(){
        return "환영합니다.";
    }

    @GetMapping("/main3")
    @ResponseBody
    public String showHome3(){
        return "반갑습니다.";
    }


    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease(){
        num++;
        return num;
    }

    @GetMapping("/cookieIncrease")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int countInCookie = 0;

        if (req.getCookies() != null) {
            countInCookie = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("count"))
                .map(Cookie::getValue)
                .mapToInt(Integer::parseInt)
                .findFirst()
                .orElse(0);
        }

        int newCountInCookie = countInCookie + 1;

        resp.addCookie(new Cookie("count", newCountInCookie + ""));

        return newCountInCookie;
    }


    @GetMapping("/reqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        resp.getWriter().append("Hello, I'm %d years old.");
    }



    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(int a, @RequestParam(defaultValue = "0") int b){
        return a+b;
    }

    @GetMapping("/returnReturnBoolean")
    @ResponseBody
    public boolean showReturnBoolean(){
        return true;
    }

    @GetMapping("/showReturnDouble")
    @ResponseBody
    public double showReturnDouble(){
        return Math.PI;
    }

    @GetMapping("/showReturnArray")
    @ResponseBody
    public int[] showReturnArray(){
        int[] arr = new int[]{10, 20, 30};
        return arr;
    }


    @GetMapping("/showReturnList")
    @ResponseBody
    public List<Integer> showReturnList(){
//        List<Integer> list = new ArrayList<>();
//        list.add(10);
//        list.add(10);
//        list.add(10);
//        return list;

        List<Integer> list = new ArrayList<>(){{
            add(10);
            add(20);
            add(20);
        }};
        return list;
    }

    @GetMapping("/showReturnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("subject", "스프링부트는 무엇인가?");
        map.put("article", "스프링부트는 어렵다.");
        map.put("articleNo", Arrays.asList(1, 2, 3));
        return map;
    }

//    @GetMapping("/showReturnArticle")
//    @ResponseBody
//    public Article showReturnArticle() {
//        Article article = new Article(1, "제목1", "내용1", new ArrayList<>() {{
//                add(1);
//                add(2);
//                add(3);
//            }});
//        return article;
//    }


//    @GetMapping("/showReturnArticle2")
//    @ResponseBody
//    public Article2 showReturnArticle2() {
//        Article2 article2 = new Article2(1, "제목1", "내용1", new ArrayList<>() {{
//            add(1);
//            add(2);
//            add(3);
//        }});
//        return article2;
//    }
//


    @GetMapping("/showReturnMapList")
    @ResponseBody
    public List<Map<String, Object>> showReturnMapList() {
        Map<String, Object> articleMap1 = new LinkedHashMap<>(){{
            put("id", 1);
            put("subject", "제목1");
            put("content", "내용1");
            put("articleNo", new ArrayList<>(){{
                add(1);
                add(2);
                add(3);
            }});
        }};

        Map<String, Object> articleMap2 = new LinkedHashMap<>(){{
            put("id", 2);
            put("subject", "제목2");
            put("content", "내용2");
            put("articleNo", new ArrayList<>(){{
                add(4);
                add(5);
                add(6);
            }});
        }};

        List<Map<String, Object>> list = new ArrayList<>(){{
           add(articleMap1);
           add(articleMap2);
        }};
        return list;
    }

    @GetMapping("/showReturnArticleList")
    @ResponseBody
    public List<Article2> showReturnArticleList() {
        Article2 article1 = new Article2(1, "제목1", "내용1", new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }});

        Article2 article2 = new Article2(1, "제목2", "내용2", new ArrayList<>(){{
            add(4);
            add(5);
            add(6);
        }});

        List<Article2> list = new ArrayList<>(){{
            add(article1);
            add(article2);
        }};

        System.out.println(list.get(0));
        System.out.println(list.get(1));

        return list;
    }

    @GetMapping("/addPerson")
    @ResponseBody
    public String addPerson(String name, int age){
        Person p = new Person(name, age);
        System.out.println(p);

        personList.add(p);

        return "%d번 사람이 추가 되었습니다.".formatted(p.getId());
    }

    @GetMapping("/personTestCase")
    @ResponseBody
    public String personTestCase(){
        personList.add(new Person("홍길동",11));
        personList.add(new Person("홍길순",22));
        personList.add(new Person("임꺽정",33));

        return "테스트케이스 추가";
    }

    @GetMapping("/removePerson")
    @ResponseBody
    public String removePerson(int id){
        boolean removed = personList.removeIf(p -> p.getId() == id);
        System.out.println(removed);
        if (removed) {
            return "%d번 사람이 삭제되었습니다.".formatted(id);
        } else {
            return "%d번 사람이 없습니다.".formatted(id);
        }
    }

    @GetMapping("/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id,
                               @RequestParam(defaultValue = "이름없음") String name,
                               @RequestParam(defaultValue = "0") int age){
        Person person = personList.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);

        if (person != null) {
            person.setName(name);
            person.setAge(age);
            return "%d번 사람이 수정되었습니다. (이름: %s, 나이: %d)".formatted(id, name, age);
        } else {
            return "%d번 사람이 없습니다.".formatted(id);
        }
    }

    @GetMapping("/showPeople")
    @ResponseBody
    public List<Person> showPeople(){
        return personList;
    }

}

class Article{
    private final int id;
    private String subject;
    private String content;
    private List<Integer> articleNo;

    public Article(int id, String subject, String content, List<Integer> articleNo) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.articleNo = articleNo;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getArticleNo() {
        return articleNo;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setArticleNo(List<Integer> articleNo) {
        this.articleNo = articleNo;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", content='" + content + '\'' +
            ", articleNo=" + articleNo +
            '}';
    }
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class Article2 {
    private final int id;
    private String subject;
    private String content;
    private List<Integer> articleNo;
}

@AllArgsConstructor
@Getter
@Setter
@ToString
class Person{
    private static int lastId;
    private final int id;
    private String name;
    private int age;

    static {
        lastId = 0;
    }

    public Person(String name, int age) {
        this(++lastId, name, age);
    }
}