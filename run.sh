for t in 10000 15000 20000 25000 30000
do
  echo "" >  ./results/record$t.txt
  echo $t >> ./results/record$t.txt
  for i in {1..3}
  do
     javac ParallelQuicksort.java && java -Xmx6g ParallelQuicksort $t $i >> ./results/record$t.txt
  done
done
