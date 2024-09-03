import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {
  public static final String TODO_CSV_FILE = "./src/resources/todo.csv";

  ArrayList<Todo> todolist = new ArrayList<>();

  public TodoList() {
    try (Scanner sc = new Scanner(new File(TODO_CSV_FILE))) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine();

        String[] fields = line.split(",");
        if (fields.length != 2) {
          continue;
        }

        Todo todo = new Todo(fields[0], fields[1]);
        append(todo);
      } // end while
    } catch (IOException e) {
      System.out.println("指定されたファイルが見つかりません");
    } // end try
  }

  /**
   * TODOデータを格納する
   * @param todo
   */
  public void append(Todo todo) {
    todolist.add(todo);
  }

  /**
   * TodoListを返す
   */
  public ArrayList<Todo> getTodoList() {
    return todolist;
  }

  public void showTodoList() {
    for(Todo todo : todolist) {
      System.out.println(todo.getId() + ", " + todo.getTitle());
    }
  }

  public void post(Scanner sc) {
    System.out.println("タスクの内容を入力してください");

    // 最新のIDを作成する
    // TODO メソッド化
    String newlastId = "1";
    if (!todolist.isEmpty()) {
      String currentLastId = todolist.getLast().getId();
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
      append(new Todo(newlastId, content));

      System.out.println("入力内容を登録しました");
      showTodoList();

    } catch (IOException e) {
      System.out.println("登録に失敗しました");
    }

  }

  public void removeTodo(Scanner sc) {
    System.out.println("削除する番号を入力してください");
    showTodoList();


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
      String content = todolist.get(i).getTitle();
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

        String writeContent = String.join(",", todo.getId(), todo.getTitle());

        bf.write(writeContent);
        bf.write("\n");

      } catch (IOException e) {
        System.out.println("データ更新処理に失敗しました");
      }

    }

    showTodoList();
  }
}
