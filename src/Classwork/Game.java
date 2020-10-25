package Classwork;

import javax.swing.*;

public class Game {

    private GameBoard board;
    private GamePlayer[] players = new GamePlayer[2];
    private static int playersTurn = 0;         // индекс текущего игрока

    public Game() {
        this.board = new GameBoard(this);
    }

    // инициация игры
    public void initGame() {
        players[0] = new GamePlayer(true, 'X');
        players[1] = new GamePlayer(false, 'O');
    }

    // передача хода
    public void passTurn() {
        if (playersTurn == 0) {
            playersTurn = 1;
        } else playersTurn = 0;
    }

    // получение текущего игрока
    GamePlayer getCurrentPlayer() {
        return players[playersTurn];
    }

    // вывод инфо-сообщений
    public void showMessage(String messageText) {
        JOptionPane.showMessageDialog(board, messageText);
    }
}
