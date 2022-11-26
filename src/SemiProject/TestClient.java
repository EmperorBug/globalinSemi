package SemiProject;




import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class TestClient {
    static ClientMenu clientMenu = new ClientMenu();
    static Scanner sc = new Scanner(System.in);

    static DataOutputStream dos;
    static DataInputStream dis;
    static JsonArray jarr = new JsonArray();


    public static void main(String[] args) {
        try {
            Socket soc = new Socket("localhost",9999);
             dos = new DataOutputStream(soc.getOutputStream());
             dis = new DataInputStream(soc.getInputStream());

            String select = "";
            boolean flag = true;
            clientMenu.getHeader();
            clientMenu.getMenu();

            while (flag) {
                select = sc.nextLine();
                switch (select) {
                    case "0" -> {
                        dos.writeUTF("close");
                        flag = false;
                        dos.close();
                        dis.close();
                        soc.close();
                    }
                    case "1" -> {
                        dos.writeUTF("read");
                        getList();
                    }
                    case "2" -> {
                        dos.writeUTF("write");
                        makeData();
                    }
                    case "3" -> content();
                    case "4" -> delete();
                }
            }
        }
        catch (EOFException e) {
            System.out.println("종료되었습니다.");
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //삭제함수 시작
    private static void delete() throws IOException {
        if (jarr.size() == 0) {
            clientMenu.getHeader();
            System.out.println("글 목록을 불러와 주세요.");
            clientMenu.getMenu();
            return;
        }
        dos.writeUTF("delete");
        makeList(); //목록 보여주기
        System.out.println();
        System.out.print("위의 게시판 번호중 하나를 넣어 주세요. >>>");
        if (!sc.hasNextInt()) {
            System.out.print("숫자를 입력해주세요");
            sc.next();
        } else {
            String content = sc.nextLine();

            dos.writeUTF(content);
            System.out.println("삭제완료하였습니다");

            clientMenu.getHeader();
            clientMenu.getMenu();
        }
    }//삭제함수 끝

    //내용
    private static void content() {
        if (jarr.size() == 0) {
            clientMenu.getHeader();
            System.out.println("글 목록을 불러와주세요.");
            clientMenu.getMenu();
            return;
        }
        makeList();
        System.out.println();
        System.out.print("위의 게시판 번호중 하나를 넣어 주세요. >>>");
        if (!sc.hasNextInt()) {
            System.out.print("숫자를 입력해주세요");
            sc.next();
        } else {
            int content = sc.nextInt();
            clientMenu.getContentHeader();
            System.out.print( content + "\t\t");
            System.out.print(jarr.get(content-1).getAsJsonObject().get("title").getAsString() + "\t\t");
            System.out.print(jarr.get(content-1).getAsJsonObject().get("content").getAsString() + "\t\t");
            System.out.print(jarr.get(content-1).getAsJsonObject().get("writer").getAsString() + "\t\t");
            System.out.print(jarr.get(content-1).getAsJsonObject().get("RegDate").getAsString() + "\n");
            clientMenu.getMenu();
        }
    }
    //내용 끝

    //등록 함수
    private static void makeData() throws IOException {
        Gson gson = new Gson();
        JsonObject jobj = new JsonObject();

        System.out.print("제목>>>");
        String title = sc.next();

        System.out.print("내용>>>");
        String content = sc.next();

        System.out.print("작성자>>>");
        String writer = sc.next();

        jobj.addProperty("title", title);
        jobj.addProperty("content", content);
        jobj.addProperty("writer", writer);

        dos.writeUTF(gson.toJson(jobj));
        System.out.println(jobj);
        System.out.println("글이 정상적으로 추가되었습니다");
        clientMenu.getMenu();
    }
    //등록함수 끝

    //목록 서버통신부분
    static void getList() throws Exception {
        Gson gson = new Gson();
        String serverString = dis.readUTF();
        if (serverString.equals("파일이 없습니다.")) {
            clientMenu.getHeader();
            System.out.println("등록된 게시글이 없습니다. 등록을 해주세요");
            clientMenu.getMenu();
        }
        else {
            jarr = gson.fromJson(serverString, JsonArray.class);
            makeList();
        }
    }
    //서버통신부분 끝

    static void makeList() {
        clientMenu.getHeader();
        if (jarr.size() == 0 )
            System.out.println("등록된 글이 없습니다");
        else {
            for (int i = 0; i < jarr.size(); i++) {
                JsonObject tempJobj = jarr.get(i).getAsJsonObject();

                System.out.print(i + 1 + "\t\t");
                listPrint(tempJobj);
            }
        }
        clientMenu.getMenu();
    }

    static void listPrint(JsonObject obj) {
        System.out.print(obj.get("title").getAsString() + "\t\t");
        System.out.print(obj.get("writer").getAsString() + "\t\t");
        System.out.print(obj.get("RegDate").getAsString() + "\n");
    }


}
