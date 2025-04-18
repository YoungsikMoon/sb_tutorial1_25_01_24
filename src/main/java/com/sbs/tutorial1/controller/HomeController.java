package com.sbs.tutorial1.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class HomeController {
    int num;
    List<Person> personList;

    public HomeController(){
        num=-1;
        personList = new ArrayList<>();

    }

    @GetMapping("/home/main1")
    @ResponseBody
    public String showHome() {
        return "어서오세요.";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showHome2(){
        return "환영합니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showHome3(){
        return "반갑습니다.";
    }


    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease(){
        num++;
        return num;
    }


    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(int a, @RequestParam(defaultValue = "0") int b){
        return a+b;
    }

    @GetMapping("/home/returnReturnBoolean")
    @ResponseBody
    public boolean showReturnBoolean(){
        return true;
    }

    @GetMapping("/home/showReturnDouble")
    @ResponseBody
    public double showReturnDouble(){
        return Math.PI;
    }

    @GetMapping("/home/showReturnArray")
    @ResponseBody
    public int[] showReturnArray(){
        int[] arr = new int[]{10, 20, 30};
        return arr;
    }


    @GetMapping("/home/showReturnList")
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

    @GetMapping("/home/showReturnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("subject", "스프링부트는 무엇인가?");
        map.put("article", "스프링부트는 어렵다.");
        map.put("articleNo", Arrays.asList(1, 2, 3));
        return map;
    }

//    @GetMapping("/home/showReturnArticle")
//    @ResponseBody
//    public Article showReturnArticle() {
//        Article article = new Article(1, "제목1", "내용1", new ArrayList<>() {{
//                add(1);
//                add(2);
//                add(3);
//            }});
//        return article;
//    }


//    @GetMapping("/home/showReturnArticle2")
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


    @GetMapping("/home/showReturnMapList")
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

    @GetMapping("/home/showReturnArticleList")
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

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age){
        Person p = new Person(name, age);
        System.out.println(p);

        personList.add(p);

        return "%d번 사람이 추가 되었습니다.".formatted(p.getId());
    }

    @GetMapping("/home/showPeople")
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