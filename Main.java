import java.util.Scanner;

enum OPE {
  CREATE
}

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String input = "";
    
    boolean isLoop = true;
    if(input.equalsIgnoreCase("exit")) {
      isLoop = false;
    }


    while(isLoop) {
      System.out.println("操作番号を選択してください");
      System.out.println("アプリを終了するにはqを入力してください");
      
      input = sc.nextLine();
      
      switch(input) {
        case "1":
          System.out.println("一覧表示");
          break;
          
        case "2":
          System.out.println("新規作成");
          break;
        
        case "3":
          System.out.println("削除");
          break;
        
        default:
          System.out.println("指定した操作は存在しません");
          break;
      }
      

      if(input.equalsIgnoreCase("exit")) {
        isLoop = false;
      }

      
      System.out.println("\n");
    }
    
    System.out.println("アプリを終了します\n");
    
    sc.close();
  }
  
}
