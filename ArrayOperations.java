/**
 * You need to implement reverseElements method. It should reverse all elements
 * in the rows of the twoDimArray as in the example below.
 *
 * Example:
 *
 * 0 0 9 9              9 9 0 0
 * 1 2 3 4 will become: 4 3 2 1
 * 5 6 7 8              8 7 6 5
 * It is guaranteed that twoDimArray has at least 1 row.
 *
 * P.S. You don't need to print anything in this task.
 */

class ArrayOperations {
    public static void reverseElements(int[][] twoDimArray) {
        // write your code here
        int tmp;
        int rowLen;

        for (int i = 0; i < twoDimArray.length; i++) {
            rowLen = twoDimArray[i].length;
            for (int j = 0; j < rowLen / 2; j++) {
                tmp = twoDimArray[i][rowLen - j - 1];
                twoDimArray[i][rowLen - j - 1] = twoDimArray[i][j];
                twoDimArray[i][j] = tmp;
            }
        }
    }
}