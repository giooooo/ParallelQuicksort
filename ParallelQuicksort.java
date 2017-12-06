
import java.util.Random;

public class ParallelQuicksort {

  public ParallelQuicksort() {}

  public void printArray(int[] array) {
    for(int i = 0; i < array.length; i++)
      System.out.println(array[i] + "\t");
    System.out.println();
  }

  public int[] createArray(int n) {
    int[] array = new int[n];
    for(int i = 0; i < array.length; i++)
      array[i] = new Random().nextInt(100000);
    return array;
  }

  public static int randomN(int bound) {
    return new Random().nextInt(bound);
  }

  public int[] parallelSort(int[] array, int low, int high) {
    if(low < high) {
      int index = partition(array, low, high);
      new Thread(new Runnable() {
        public void run() {
          parallelSort(array, low, index-1);
        }
      }).start();
      new Thread(new Runnable() {
        public void run() {
          parallelSort(array, index+1, high);
        }
      }).start();
    }
    return array;
  }
  public int[] seriesSort(int[] array, int low, int high) {
    if(low < high) {
      int index = partition(array, low, high);
      seriesSort(array, low, index-1);
      seriesSort(array, index+1, high);
    }
    return array;
  }

  public int partition(int[] array, int low, int high) {
    int pivot = array[high];
    int i = low - 1;
    int j = 0;
    for(j = low; j < high; j++) {
      if(array[j] <= pivot) {
        i++;
        array[i] = array[i] ^ array[j] ^ (array[j] = array[i]);
      }
    }
    array[i+1] = array[i+1] ^ array[j] ^ (array[j] = array[i+1]);
    return i + 1;
  }

  public static void main(String[] args) {
    ParallelQuicksort pq = new ParallelQuicksort();
    int n = 0, run = 0;
    try {
      n = Integer.parseInt(args[0]);
      run = Integer.parseInt(args[1]);
    } catch(NumberFormatException e) {
      e.printStackTrace();
    }
    final int[] array = pq.createArray(n);
    final int[] array2 = array.clone();
    System.out.println(run + "");
    final long startSeries = System.currentTimeMillis();
    pq.seriesSort(array, 0, n-1);
    System.out.println((System.currentTimeMillis() - startSeries) + " ms");

    final long startParallel = System.currentTimeMillis();
    pq.parallelSort(array2, 0, n-1);
    System.currentTimeMillis();
    System.out.println((System.currentTimeMillis() - startParallel) + " ms");
    System.out.println();
  }
}
