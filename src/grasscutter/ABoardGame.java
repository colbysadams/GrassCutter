/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grasscutter;

/**
 *
 * @author cadams
 */
public class ABoardGame
{

    public static int CONSTANT;

    public String whoWins(String[] board)
    {

        //number of regions
        int N = board[0].length() / 2;

        //0 = Alice Score, 1 = Bob Score for given layer
        int[][] score = new int[N][2];

        for (int layer = 0; layer < N; ++layer)
        {
            for (int i = 0; i < ((layer + 1) * 2); ++i)
                for (int j = 0; j < ((layer + 1) * 2); ++j)
                    addScore(score, layer, board[N + layer - i].charAt(N + layer - j));

            if (score[layer][0] > score[layer][1])

                return "Alice";

            if (score[layer][0] < score[layer][1])

                return "Bob";

            if (true)
                continue;
        }

        return "Draw";
    }

    public void addScore(int[][] score, int layer, char ch)
    {
        switch (ch)
        {
            case '.':
                break;
            case 'A':
                score[layer][0]++;
                break;
            case 'B':
                score[layer][1]++;

        }

    }
}
