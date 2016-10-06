/*
 In a galaxy far far away... each week has N days.

 Luke Skywalker has exactly N shirts.

 The shirts are numbered 1 through N.

 Each day he wears one of those N shirts.

 Each week he wears each shirt exactly once.



 In different weeks Luke may wear his shirts in different orders.

 However, not all orders are always possible.

 Whenever Luke wears a shirt for a day, he has to wash it before he can use
 it again.

 Washing and drying a shirt takes D-1 full days.

 In other words, if he wears a shirt on day x, the earliest day when he can
 wear it again is day x+D.



 Master Yoda recently sent Luke on a training mission that lasted for some 
 unknown number of full N-day weeks. He remembers the order in which he wore
 his shirts during the first week of the mission. He also remembers the order
 in which he wore his shirts during the last week of the mission. You are given
 this information in int[]s firstWeek and lastWeek. Each of these int[]s
 contains N elements: the numbers of shirts he wore during that week, in order.
 You are also given the number of days D that it takes to wash a shirt.



 For example, assume that N = 4, firstWeek = {1,2,3,4}, and 
 lastWeek = {4,3,2,1} and D = 3. 
 
 It is possible that this particular mission took four weeks.
 One possible order in which Luke could have worn his shirts looks as follows:
 week 1: {1,2,3,4}
 week 2: {2,3,4,1}
 week 3: {3,4,2,1}
 week 4: {4,3,2,1}
 Given firstWeek, lastWeek and D, compute and return the smallest number
 of weeks the mission could have taken.

 Definition

 Class:	ANewHope
 Method:	count
 Parameters:	int[], int[], int
 Returns:	int
 Method signature:	int count(int[] firstWeek, int[] lastWeek, int D)
 (be sure your method is public)


 Notes
 -	N can be calculated as the number of elements of firstWeek

 Constraints
 -	firstWeek will contain between 2 and 2500 integers inclusive.
 -	firstWeek and lastWeek will contain the same number of elements.
 -	firstWeek and lastWeek will represent permutations of the first N positive integers.
 -	D will be between 1 and N-1 inclusive.

 Examples
 0)

 {1,2,3,4}
 {4,3,2,1}
 3
 Returns: 4
 The example from the problem statement.
 1)

 {8,5,4,1,7,6,3,2}
 {2,4,6,8,1,3,5,7}
 3
 Returns: 3
 2)

 {1,2,3,4}
 {1,2,3,4}
 2
 Returns: 1
 Be careful, the first week and the last week can be the same week.

 This problem statement is the exclusive and proprietary property of TopCoder, Inc.
 Any unauthorized use or reproduction of this information without the prior written
 consent of TopCoder, Inc. is strictly prohibited. (c)2010, TopCoder, Inc.
 All rights reserved.
 */
package grasscutter;

public class ANewHope
{

    public int count(int[] firstWeek, int[] lastWeek, int D)
    {

        int N = firstWeek.length;
        int followingIsSorted = N;
        int[] daysToFinalWash = new int[N + 1];
        int[] currentWeek = firstWeek.clone();

        int count = 1;
        boolean sameWeek = true;

        // check to make sure that firstWeek and lastWeek aren't the same
        for (int i = 0; i < N; ++i)
        {
            if (firstWeek[i] != lastWeek[i])
            {
                sameWeek = false;
                break;
            }
        }
        //if they are the same, then just return 1 and be done with it
        if (sameWeek)
        {
            return count;
        }

        boolean needNewWeek;
        do
        {
            needNewWeek = false;
            ++count;

            daysToFinalWash = new int[N + 1];

            for (int i = 0; i < N; i++)
            {
                int j = 0;

                while (lastWeek[j] != currentWeek[i])
                {
                    ++j;
                }

                //this is the number of days between wears if next week were
                //the final week
                daysToFinalWash[currentWeek[i]] = N - i + j;

            }

            //if any shirt has fewer than D days between wears
            //we're going to need to add another week
            for (int i = 0; i < N; ++i)
            {
                if (daysToFinalWash[i + 1] < D)
                {
                    needNewWeek = true;

                    break;
                }

            }

            /*
             * You're reading that correctly, it says bubble sort
             */
            followingIsSorted = bubbleSortByWash(currentWeek,
                                                 daysToFinalWash,
                                                 lastWeek,
                                                 D,
                                                 followingIsSorted);

            //print info for the current week
            System.out.print("Week " + count + ": ");
            printArray(currentWeek);
        } while (needNewWeek);

        return count;
    }

    /**
     *
     * Not your typical bubble sort
     * <p>
     * <p>
     * <p>
     * @param previousWeek      - the week that just passed (will be modified to
     *                          be the current week)
     * @param daysToFinalWash   - how many days between wearing the same shirt
     *                          for the current week and the final week
     *                          (assuming next week will be the final week if
     *                          possible)
     * @param lastWeek          - what you wore on the final week of the trip
     * @param D                 - the minimum number of days between wearing the
     *                          same shirt
     * @param followingIsSorted - ignore all values after this index (inclusive)
     * <p>
     * @return
     */
    public int bubbleSortByWash(int[] previousWeek,
                                int[] daysToFinalWash,
                                int[] lastWeek,
                                int D,
                                int followingIsSorted)
    {
        //bubble sort by daysToWash and update daysToWash as you go
        int n = previousWeek.length;
        int[] daysSinceWash = new int[n + 1];
        for (int i = 0; i < n; ++i)
        {
            daysSinceWash[i + 1] = n;
        }

        int k;
        int j;

        /*
         * the first loop looks for the "shirts" that will be worn at the end
         * of the week and bubbles them up to the end first. This way the
         * end of the list is already sorted and we can skip that part of the
         * list in the next iteration by using the followingIsSorted variable.
         *
         * Note that bubble sort is used because the daysSinceWash
         * and daysToFinalWash value for each shirt must be changed when each
         * shirt is moved around.
         */
        for (int m = followingIsSorted - 1; m >= 0; --m)
        {
            for (int i = 0; i < m - 1; ++i)
            {
                j = (i);
                k = (i + 1);
                if (previousWeek[j] == lastWeek[m] && daysSinceWash[previousWeek[k]] > D)
                {
                    if (j == m)
                    {
                        followingIsSorted = m;
                    }
                    swap(j, k, previousWeek, daysToFinalWash, daysSinceWash);
                }
            }
        }
        /*
         * now that we have bubbled up as many shirts to the end of the list as
         * we can, move all other shirts as close to their final position as
         * possible.
         *
         */
        for (int m = followingIsSorted - 1; m >= 0; --m)
        {

            for (int i = 0; i < m; ++i)
            {
                j = (i);
                k = (i + 1);
                if (daysSinceWash[previousWeek[k]] <= D)
                {
                    continue;
                }
                if (daysToFinalWash[previousWeek[j]] > n || daysToFinalWash[previousWeek[k]] < D)
                {
                    swap(j, k, previousWeek, daysToFinalWash, daysSinceWash);
                }
            }
        }

        //quick check to make sure that no rules have been broken
        //ie no shirt is being worn before D days have passed
        for (int i = 0; i < n; ++i)
        {
            if (daysSinceWash[i + 1] < D)
            {
                throw new RuntimeException("not enough days have passed for : " + i
                        + " days: " + daysSinceWash[i]);
            }
        }
        return followingIsSorted;
    }

    /**
     *
     * Switch method for the bubble sort that updates daysSinceWash and
     * daysToFinalWash
     * <p>
     * <p>
     * <p>
     * @param indexOfNum1
     * @param indexOfNum2
     * @param array
     * @param daysToWash
     * @param daysSinceWash
     */
    public void swap(int indexOfNum1,
                     int indexOfNum2,
                     int[] array,
                     int[] daysToWash,
                     int[] daysSinceWash)
    {

        daysSinceWash[array[indexOfNum1]]++;
        daysSinceWash[array[indexOfNum2]]--;

        daysToWash[array[indexOfNum1]]--;
        daysToWash[array[indexOfNum2]]++;

        int temp = array[indexOfNum1];
        array[indexOfNum1] = array[indexOfNum2];
        array[indexOfNum2] = temp;

    }

    /**
     * print that array all pertty like
     * <p>
     * @param array
     */
    public void printArray(int[] array)
    {
        if (array[0] != 0)
        {
            System.out.print("   ");
        }
        System.out.print("[");
        for (int i = 0; i < array.length; ++i)
        {
            System.out.print(array[i] + ", ");
        }
        System.out.println("]");
    }

    /**
     * verified version from the interwebs for checking correct solutions
     * <p>
     * theirs works much quicker, but only gives you the number, doesn't
     * actually
     * figure out what each week would be.
     * <p>
     * @param fs
     * @param ls
     * @param d
     *           <p>
     * @return
     */
    public static int count2(int[] fs, int[] ls, int d)
    {
        int n = fs.length;
        boolean same = true;
        for (int i = 0; i < n; i++)
        {
            same &= fs[i] == ls[i];
        }
        if (same)
        {
            return 1;
        }
        int[] as = new int[n], bs = new int[n];
        for (int i = 0; i < n; i++)
        {
            as[fs[i] - 1] = i;
            bs[ls[i] - 1] = i;
        }
        int min = 0;
        for (int i = 0; i < n; i++)
        {
            min = Math.min(min, bs[i] - as[i]);
        }
        System.out.println(min);

        return (-min + (n - d - 1)) / (n - d) + 1;
    }
}
