import java.util.*;
import java.io.*;

public class Main {

  public static final String TODO_CSV_FILE = "./data/todo.csv";

  ArrayList<Todo> todolist = new ArrayList<>();

  public static void main(String[] args) {

    Main mainIns = new Main();

    mainIns.storeTodoData();

    try (Scanner sc = new Scanner(System.in)) {

      boolean isLoop = true;

      while (isLoop) {
        mainIns.showOperation();

        String input = sc.nextLine();

        switch (input) {
          case "1":
            mainIns.showAllTaskMessage();
            mainIns.showAllTask();
            break;

          case "2":
            mainIns.post(sc);
            break;

          case "3":
            mainIns.delete(sc, mainIns);
            break;

          default:
            System.out.println("アプリを終了します\n");
            isLoop = false;
            break;
        }

        System.out.println("\n");
      }


    }
  }

  /**
   * データファイルを読み込んでセットする
   */
  private void storeTodoData() {
    try (Scanner sc = new Scanner(new File(TODO_CSV_FILE))) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine();

        String[] fields = line.split(",");
        if (fields.length != 2) {
          continue;
        }

        Todo todo = new Todo(fields[0], fields[1]);
        todolist.add(todo);
      } // end while
    } catch (IOException e) {
      System.out.println("指定されたファイルが見つかりません");
    }
  }


  private void showOperation() {
    System.out.println("操作番号を選択してください");
    System.out.println("1: 表示");
    System.out.println("2: 作成");
    System.out.println("3: 削除");
    System.out.println("4: 終了");
  }

  private void showAllTask() {
    // TODO 表示方法変更する
    for (Todo todo : todolist) {
      System.out.println(todo.getId() + "," + todo.getContent());
    }
  }

  private void showAllTaskMessage() {
    System.out.println("\nすべてのタスクを表示します");
  }

  private void post(Scanner sc) {
    System.out.println("タスクの内容を入力してください");

    // 最新のIDを作成する
    // TODO メソッド化
    String newlastId = "1";
    if (!todolist.isEmpty()) {
      String currentLastId = todolist.get(todolist.size() - 1).getId();
      try {
        int number = Integer.parseInt(currentLastId) + 1;
        newlastId = String.valueOf(number);
      } catch (NumberFormatException e) {
        System.out.println("IDの変換処理に失敗しました");
      }
    }


    // 標準入力からデータを取得する
    // TODO ファイル更新前にバックアップファイルを作る
    String content = sc.nextLine();

    // IDと入力データをカンマ区切りでファイルに書き込む
    String writeContent = String.join(",", newlastId, content);

    try (BufferedWriter bf = new BufferedWriter(new FileWriter(TODO_CSV_FILE, true))) {
      bf.write(writeContent);
      bf.write("\n");

      // storeに新規データを登録する
      todolist.add(new Todo(newlastId, content));

      System.out.println("入力内容を登録しました");
      showAllTaskMessage();
      showAllTask();

    } catch (IOException e) {
      System.out.println("登録に失敗しました");
    }

  }


  /**
   * 画面にファイルに記録しているデータを表示する 削除する番号を入力する 入力した番号を取得する storeから一致するデータを取得する storeから削除する
   * IDを振り直してstoreを更新する ファイルに保存する
   */
  private void delete(Scanner sc, Main mainIns) {
    System.out.println("削除する番号を入力してください");
    mainIns.showAllTask();


    // 削除ID存在チェック
    // TODO メソッド化する
    String stringDeleteId = sc.nextLine();
    System.out.println(stringDeleteId + "番目のタスクを削除します");

    try {
      int intDeleteId = Integer.parseInt(stringDeleteId) - 1;
      if (intDeleteId < 0 || todolist.size() <= intDeleteId) {
        throw new NumberFormatException();
      }
      todolist.remove(intDeleteId);
    } catch (NumberFormatException e) {
      System.out.println("入力した番号のデータは存在しません");
      return;
    }

    // storeを更新する
    ArrayList<Todo> tmpTodo = new ArrayList<>();
    for (int i = 0; i < todolist.size(); i++) {
      String id = String.valueOf(i + 1);
      String content = todolist.get(i).getContent();
      tmpTodo.add(new Todo(id, content));
    }

    // 更新した値を入れ替える
    todolist = tmpTodo;

    // TODO 更新前にバックアップファイルを作る
    // ファイルを空にする
    try (FileWriter fw = new FileWriter(TODO_CSV_FILE, false)) {
    } catch (IOException e) {
      System.out.println("データ更新処理に失敗しました");
    }

    // データファイル更新処理
    for (Todo todo : todolist) {

      try (BufferedWriter bf = new BufferedWriter(new FileWriter(TODO_CSV_FILE, true))) {

        String writeContent = String.join(",", todo.getId(), todo.getContent());

        bf.write(writeContent);
        bf.write("\n");

      } catch (IOException e) {
        System.out.println("データ更新処理に失敗しました");
      }

    }

    showAllTaskMessage();
    showAllTask();


  }

}
