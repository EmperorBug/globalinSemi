package SemiProject;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public  class  Board implements Runnable{
    private final Socket soc;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String name;
    List<BoardDTO> list = new LinkedList<>();
    public Board(Socket soc) throws Exception{
        dis = new DataInputStream(soc.getInputStream());
        this.soc = soc;
        dos = new DataOutputStream(soc.getOutputStream());
        name = "["+soc.getInetAddress()+":"+soc.getPort()+"]";
    }

    @Override
    public void run() {
        System.out.println(name+"님이 연결되었습니다.");
        while (true) {
            try {
                if (!soc.isClosed()) {

                    String num = dis.readUTF();
                    System.out.println(name+"님으로부터의 요청입니다 ==> "+num);
                    switch (num) {
                        case "close" -> close();
                        case "read" -> read();
                        case "write" -> write();
                        case "delete" -> delete();
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        dos.close();
        dis.close();
        soc.close();
    }

    public void read() throws IOException {
        try {
            Gson gson = new Gson();
            JsonArray jarr = new JsonArray();
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String str = "";

            while ((str = br.readLine()) != null) {
                JsonObject temp = new JsonObject();
                String[] tmpArr = str.split("&");
                temp.addProperty("title",tmpArr[0]);
                temp.addProperty("writer",tmpArr[1]);
                temp.addProperty("content",tmpArr[2]);
                temp.addProperty("RegDate",tmpArr[3]);
                jarr.add(temp);
                //리스트에 추가해서 삭제할때 사용
                list.add(new BoardDTO(tmpArr[0],tmpArr[1], tmpArr[2], tmpArr[3]));
            }

            dos.writeUTF(gson.toJson(jarr));
            br.close();
        }
        catch (Exception e) {
            dos.writeUTF("파일이 없습니다.");
        }
    }

    public void write() throws Exception{
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(dis.readUTF(), Map.class);

        String title = (String) map.get("title");
        String writer = (String) map.get("writer");
        String content = (String) map.get("content");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy, h:mm:ss a",new Locale("en","US"));

        BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt",true));
        bw.write(title+"&"+writer+"&"+content+"&"+dateFormat.format(now));
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public void delete() throws Exception {
        int delLine = Integer.parseInt(dis.readUTF());
        String delString = list.get(delLine-1).getTitle()+"&"+list.get(delLine-1).getWriter()+"&"+list.get(delLine-1).getContent()+"&"+list.get(delLine-1).getRegDate();
        BufferedReader br = new BufferedReader(new FileReader("data.txt"));
        String str = "";
        String newString = "";

        while ((str = br.readLine()) != null) {
            if (str.equals(delString)) continue; //삭제할 줄이면 건너뜀

            newString+=str+"\n";
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));
        bw.write(newString);
        bw.flush();
        bw.close();
    }
}


