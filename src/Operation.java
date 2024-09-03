public class Operation {
  private boolean isLoop = false;

  public void showOperation() {
    System.out.println("操作番号を選択してください");
    System.out.println("1: 表示");
    System.out.println("2: 作成");
    System.out.println("3: 削除");
    System.out.println("4: 終了");
  }

  public void startOperation() {
    isLoop = true;
  }

  public void endOperation() {
    isLoop = false;
  }

  public boolean isLoopActive() {
    return isLoop;
  }
}
