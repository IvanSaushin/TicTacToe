import java.util.Random;
import java.util.Scanner;

public class TicTacSm_V2 {
    private static int size = 4;
    private static char[][] map = new char[size][size];
    private static char _X = 'X';
    private static char _O = 'O';
    private static char _EMPTY = '-';

    private static Scanner userInput = new Scanner(System.in);
    private static Random random = new Random();

    private static final boolean SILLY_MODE = false;
    private static final boolean SCORING_MODE = false;


    public static void main(String[] args) throws InterruptedException {
        initMap();
        printMap();
        startGame();
    }

    // НАЧАТЬ ИГРУ
    private static void startGame() throws InterruptedException {
        System.out.println("Start game!");

        while (isEmptyCell()) {
            humanTurn();
            if (isWin(_X)) {
                Thread.sleep(1000);
                System.out.println("Вы победили!");
                break; }

            if(!isEmptyCell()) {
                System.out.println("Поле заполнено");
                break;
            }

            computerTurn();
            if (isWin(_O)) {
                Thread.sleep(1000);
                System.out.println("Компьютер победили!");
                printMap();
                break; }
        }
        System.out.println("End Game");
    }


    // ХОД ИГРОКА
    private static void humanTurn() {
        System.out.println("Очередь игрока ");
        int x, y;
        do {
            System.out.println("Введите координаты (X Y)");
            x = userInput.nextInt()-1;
            y = userInput.nextInt()-1;
        } while (!isCorrectValid(x, y)) ;

        map[x][y] = _X;
        printMap();
        System.out.println("Ход игрока окончен.");
    }

    // ХОД COMPUTER
    static void computerTurn() {
        System.out.println("Очередь компьютера ");
        int x = 0, y = 0;

        if (SILLY_MODE) {
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!isCellValid(x, y) );
        }
        else if (SCORING_MODE) {
            boolean moveFound = false;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j] == _EMPTY) {
                        // left - up 1
                        if ( (i-1)>=0 && (j-1)>=0 && map[i-1][j-1] == _O ) {
                            x = i; y = j;
                            moveFound = true; }
                        // up 2
                         else if ( (i-1) >= 0 && map[i-1][j] == _O ) {
                            x = i; y = j;
                            moveFound = true; }
                        // right - up 3
                        else if ( (i-1) >= 0  && (j+1) < size && map[i-1][j+1] == _O) {
                            x = i; y = j;
                            moveFound = true; }
                        // left 4
                        else if ( (j-1) >= 0 && map[i][j-1] == _O) {
                            x = i; y = j;
                            moveFound = true; }
                        // right 5
                        else if ( (j+1) < size && map[i][j+1] == _O) {
                            x = i; y = j;
                            moveFound = true; }
                        // bottom - left 6
                        else if ( (i+1) < size && (j-1) >= 0 && map[i+1][j-1] == _O ) {
                            x = i; y = j;
                            moveFound = true; }
                        // bottom 7
                        else if ( (i+1) < size && map[i+1][j] == _O) {
                            x = i; y = j;
                            moveFound = true; }
                        // bottom - right 8
                        else if ( (i+1) < size && (j+1) <size && map[i][j] == _O) {
                            x = i; y = j;
                            moveFound = true; }
                    }
                    if (moveFound) { break; }
                }
                if (moveFound) { break; }
            }
        }
        // ПОДСЧЁТ ОЧКОВ КАЖДОЙ ЯЧЕЙКИ
        else {
            int maxscore = 0;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int currentCellScore=0;

                    if (map[i][j] == _EMPTY) {
                        // left - up 1
                        if ((i - 1) >= 0 && (j - 1) >= 0 && map[i - 1][j - 1] == _O) {
                            currentCellScore++;
                        }
                        // up 2
                        if ((i - 1) >= 0 && map[i - 1][j] == _O) {
                            currentCellScore++;
                        }
                        // right - up 3
                        if ((i - 1) >= 0 && (j + 1) < size && map[i - 1][j + 1] == _O) {
                            currentCellScore++;
                        }
                        // left 4
                        if ((j - 1) >= 0 && map[i][j - 1] == _O) {
                            currentCellScore++;
                        }
                        // right 5
                        if ((j + 1) < size && map[i][j + 1] == _O) {
                            currentCellScore++;
                        }
                        // bottom - left 6
                        if ((i + 1) < size && (j - 1) >= 0 && map[i + 1][j - 1] == _O) {
                            currentCellScore++;
                        }
                        // bottom 7
                        if ((i + 1) < size && map[i + 1][j] == _O) {
                            currentCellScore++;
                        }
                        // bottom - right 8
                        if ((i + 1) < size && (j + 1) < size && map[i][j] == _O) {
                            currentCellScore++;
                        }
                    }

                    if (currentCellScore>maxscore) {
                        maxscore= currentCellScore;
                        x = i; y = j;
                    }
                }
            }
        }

        //для первого хода
        if (x == 0 && y == 0) {
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!isCellValid(x, y));
        }

        map[x][y] = _O;
        System.out.println("Компьютер сходил по координатам (" +(x+1)+", "+(y+1)+")");
        printMap();
        System.out.println("Ход компьютера окончен.");
    }


    // ПРОВЕРКА ВЫИГРЫША
    static boolean isWin(char plS) {
        boolean result = false;
        if (    map[0][0] == plS && map[0][1] == plS && map[0][2] == plS ||     //1
                map[1][0] == plS && map[1][1] == plS && map[1][2] == plS ||     //2
                map[2][0] == plS && map[2][1] == plS && map[2][2] == plS ||     //3
                map[0][0] == plS && map[1][0] == plS && map[2][0] == plS ||     //4
                map[0][1] == plS && map[1][1] == plS && map[2][1] == plS ||     //5
                map[0][2] == plS && map[1][2] == plS && map[2][2] == plS ||     //6
                map[0][0] == plS && map[1][1] == plS && map[2][2] == plS ||     //7
                map[2][0] == plS && map[1][1] == plS && map[0][2] == plS) {     //8
            result = true;
        }
        return result;
    }


    // ПРОВЕРКА ЯЧЕЙКИ
    static boolean isCellValid(int x, int y) {               // Возвращает true, если ячейка пуста, равна 0, и НЕ равна x или y .
        boolean result = false;                              // falce, если ячейка РАВНА x или y .
        if (map[x][y] == _EMPTY) {result = true;}

        return result;
    }


    // ПРОВЕРКА ПОЛЯ НА ЗАПОЛНЕННОСТЬ
    private static boolean isEmptyCell() {
        int emptyCell =0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j]==_EMPTY) {
                    emptyCell++;
                    break;}
            }
        }
        if (emptyCell > 0) { return true;
        } else {return false; }
    }

    // ПРОВЕРКА НА КОРРЕКТНОСТЬ ВВОДА ККОРДИНАТ
    private static boolean isCorrectValid(int x, int y) {
        boolean result = true;

        if (x<0 || x>3 || y<0 || y>3) {
            result = false;  }

        try {  if (map[x][y] != _EMPTY) {result = false;}
        } catch (IndexOutOfBoundsException e) {  result = false; }

        return result;
    }

    // ЗАПОЛНИТЬ КАРТУ
    private static void initMap() {
        for (int i = 0; i <size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = _EMPTY;
            }
        }
    }

    // ОТОБРАЗИТЬ ПОЛЕ
    private static void printMap() {

        for (int i = 0; i <= size; i++) {
            System.out.print(i+" ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print((i+1)+" ");
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }
}
