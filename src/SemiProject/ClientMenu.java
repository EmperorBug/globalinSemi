package SemiProject;

public class ClientMenu {

    public void getHeader() {
        System.out.println("===================");
        System.out.println("번호\t\t제목\t\t작성자\t\t작성일");
    }

    public void getContentHeader() {
        System.out.println("===================");
        System.out.println("번호\t\t제목\t\t내용\t\t작성자\t\t작성일");
    }
    public void getMenu() {
        System.out.println("-------------------");
        System.out.print("1.목록\t2.등록\t3.내용\t4.삭제\t0.종료");
    }
}
