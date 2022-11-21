package SemiProject;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BoardMain {
    static List<HashMap<String, Object>> list = new ArrayList();
    static Scanner sc;

    public BoardMain() {
    }

    public static void main(String[] args) {
        try {
            boolean flag = true;

            while(flag) {
                System.out.println("===================");
                System.out.println("번호\t제목\t작성자\t작성일");
                System.out.println("-------------------");
                System.out.println("1.목록\t2.등록\t3.내용\t4.삭제\t0.종료");
                if (!sc.hasNextInt()) {
                    System.out.println("숫자를 입력해주세요");
                    sc.next();
                } else {
                    int select = sc.nextInt();
                    switch (select) {
                        case 0:
                            flag = !flag;
                            break;
                        case 1:
                            board();
                            break;
                        case 2:
                            insert();
                            break;
                        case 3:
                            content();
                            break;
                        case 4:
                            delete();
                            break;
                        default:
                            System.out.println("번호를 확인해주세요");
                    }
                }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void board() {
        if (list.size() == 0) {
            System.out.println("글 목록이 없습니다.");
        }

        for(int i = 0; i < list.size(); ++i) {
            System.out.print(i + 1 + "\t");
            PrintStream var10000 = System.out;
            HashMap var10001 = (HashMap)list.get(i);
            var10000.print(var10001.get("title") + "\t");
            var10000 = System.out;
            var10001 = (HashMap)list.get(i);
            var10000.print(var10001.get("content") + "\t");
            var10000 = System.out;
            var10001 = (HashMap)list.get(i);
            var10000.print(var10001.get("writer") + "\t\n");
        }

    }

    public static void insert() {
        Map<String, Object> map = new HashMap();
        System.out.print("제목>>>");
        String title = sc.next();
        System.out.print("내용>>>");
        String content = sc.next();
        System.out.print("작성자>>>");
        String writer = sc.next();
        LocalDate now = LocalDate.now();
        map.put("title", title);
        map.put("content", content);
        map.put("writer", writer);
        map.put("now", now);
        list.add((HashMap)map);
        System.out.println("글이 정상적으로 추가되었습니다");
    }

    public static void content() {
        System.out.println("게시글 번호를 입력해주세요");

        try {
            int num = sc.nextInt();
            PrintStream var10000 = System.out;
            HashMap var10001 = (HashMap)list.get(num - 1);
            var10000.print(var10001.get("title") + "\t");
            var10000 = System.out;
            var10001 = (HashMap)list.get(num - 1);
            var10000.print(var10001.get("content") + "\t");
            var10000 = System.out;
            var10001 = (HashMap)list.get(num - 1);
            var10000.print(var10001.get("writer") + "\t");
            var10000 = System.out;
            var10001 = (HashMap)list.get(num - 1);
            var10000.print(var10001.get("now") + "\n");
        } catch (InputMismatchException var1) {
            System.out.println("오류입니다. 다시 확인해주세요");
        } catch (IndexOutOfBoundsException var2) {
            System.out.println("번호가 없습니다. 다시 확인해주세요");
        }

    }

    public static void delete() {
        System.out.println("삭제할 번호를 입력해주세요");

        try {
            int num = sc.nextInt();
            list.remove(num - 1);
            System.out.println("삭제되었습니다.");
        } catch (InputMismatchException var1) {
            System.out.println("오류입니다. 다시 확인해주세요");
        } catch (IndexOutOfBoundsException var2) {
            System.out.println("번호가 없습니다. 다시 확인해주세요");
        }

    }

    static {
        sc = new Scanner(System.in);
    }
}