/**
 * 200. Number of Islands
 * https://leetcode.com/problems/number-of-islands/description/
 * Tag: DFS, BFS, Union Find
 */

import org.junit.*;
public class NumberOfIslands {
  int[] dx = {0, 1, 0, -1};
  int[] dy = {1, 0, -1, 0};

  public int numIslands(char[][] grid) {
    int island = 0;
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        if (grid[i][j] == '1') {
          visitNeighboredLand(i, j, grid);
          ++island;
        }
      }
    }
    return island;
  }

  @Test
  public void testNumberOfIslands() {
    char[][] grid = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
    Assert.assertEquals(3, numIslands(grid));
  }

  public void visitNeighboredLand(int row, int col, char[][] grid) {
    grid[row][col] = 'x';
    for (int i = 0; i < 4; ++i) {
      if ((row + dx[i] >= 0 && row + dx[i] < grid.length)
          && (col + dy[i] >= 0 && col + dy[i] < grid[0].length)) {
        if (grid[row + dx[i]][col + dy[i]] == '1') {
          visitNeighboredLand(row + dx[i], col + dy[i], grid);
        }
      }
    }
  }
}





