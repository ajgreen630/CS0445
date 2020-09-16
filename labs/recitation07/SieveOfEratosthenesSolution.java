package cs445.lab7;

public class SieveOfEratosthenes {
    public static ListInterface<Integer> primesUpTo(int max) {
        // Keep a list of integers that are prime
        ListInterface<Integer> primes = new ArrayList<>();
        // Add everything 2 through max
        for (int i = 2; i <= max; i++) {
            primes.add(i);
        }

        // Remove the values that are not prime using SoE
        for (int i = 2; i <= Math.sqrt(max); i++) {
            if (primes.contains(i)) {
                for (int j = i*i; j <= max; j += i) {
                    if (primes.contains(j)) {
                        primes.remove(primes.indexOf(j));
                    }
                }
            }
        }

        // Return list
        return primes;
    }

    public static ListInterface<Integer> primesUpToAlternate(int max) {
        // Keep a list of true/false values
        // Assume that true at position i indicates that i is prime
        ListInterface<Boolean> isPrime = new ArrayList<>();

        // Set 0 and 1 as degenerate cases
        isPrime.add(0, false);
        isPrime.add(1, false);
        // Set 2 through max to true
        for (int i = 2; i <= max; i++) {
            isPrime.add(i, true);
        }

        // Set values to false for indices that are not prime using SoE
        for (int i = 2; i <= Math.sqrt(max); i++) {
            if (isPrime.get(i)) {
                for (int j = i*i; j <= max; j += i) {
                    if (isPrime.get(j)) {
                        isPrime.set(j, false);
                    }
                }
            }
        }

        // Copy all indices where there is a true to a list of primes
        ListInterface<Integer> primes = new ArrayList<>();
        for (int i = 0; i < isPrime.getSize(); i++) {
            if (isPrime.get(i)) {
                primes.add(i);
            }
        }

        // Return list of prime integers
        return primes;
    }

    public static void main(String[] args) {
        int end = 0;
        try {
            end = new Integer(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Please use a integer parameter for maximum value");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please use a integer parameter for maximum value");
            return;
        }

        ListInterface<Integer> result = primesUpTo(end);
        if (result != null) {
            System.out.println("Primes:");
            for (int i = 0; i < result.getSize(); i++) {
                System.out.print(result.get(i) + " ");
            }
            System.out.println(" ");
        } else {
            System.out.println("primesUpTo() returned null. Did you complete it?");
        }
    }
}
