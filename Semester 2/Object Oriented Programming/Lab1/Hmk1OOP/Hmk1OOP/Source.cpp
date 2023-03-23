#include <stdio.h>

int zero_digit(int x)
{
	/*
	* This function counts the number of zero digits of a given number
	* It returns the number of zero digits
	*/
	int count = 0;
	do {
		if (x % 10 == 0)
			count++;
		x = x / 10;
	} while (x != 0);
	return count;
}

int prime_nr(int n)
/*
* This function verifies if a number is prime or not
* It returns 1 if the number is prime and 0 if not
*/
{
	if (n < 2)
		return 0;
	for (int d = 2; d <= n / 2; d++)
		if (n % d == 0)
			return 0;
	return 1;

}

void read(int a[], int* n)
{
	/*
	* The function read() reads the number of elements and the elements of a string
	*/
	printf("Enter the number of elements of the string: ");
	scanf_s("%d", n);
	printf("\nEnter the string elements:\n");
	for (int i = 0;i < *n;i++)
	{
		printf("\tEnter elem %d: ", i + 1);
		scanf_s("%d", &a[i]);
	}
}



void display(int a[], int n)
{
	/*
	* The function displays the elements of a string
	*/
	printf("\nString elements are: ");
	for (int i = 1;i <= n;i++)
		printf("%d ", a[i]);

}

void longest_seq(int n, int b[], int* start, int* length)
{
	/*The function returns the start index and the length of the longest contiguous
	subsequence of the array, s.t. the sum of any two consecutive numbers is prime
	*/

	int c_len = 0; // current length
	int max_l = -1; // max length
	int current_start = 0; // start index
	int max_start = 0; // the max start of an index

	int j;

	// Parse the array
	for (int i = 0; i < n; ++i)
	{
		if (prime_nr(b[i] + b[i + 1]) == 1)
		{
			j = i + 1;
			c_len = 2;
			current_start = i;

			// Keep going until we reach the end of the current subsequence.
			while ((prime_nr(b[j] + b[j + 1]) == 1) && (j + 1 <= n))
			{
				j++;
				c_len++;
			}

			i = j;

			// After finding the total length of the current subseq, compare it to the max susbeq.
			if (c_len > max_l)
			{
				max_l = c_len;
				max_start = current_start;
			}
		}
	}

	*start = max_start;
	*length = max_l;

	return;
}
//------------------------------------------------------------------------------------

void fctA() {
	int n, option, p = 1;
	int zero_counter = 0;
	while (1)
	{
		printf("Your input: ");
		scanf_s("%d", &n);

		if (n != 0)
		{
			p = p * n;
		}
		else
			break;
		zero_counter = zero_digit(p);
	}
	printf("The number of zero digits from this sequence is : ");
	printf("%d", zero_counter);
}

void fctB() {
	int b[100], start = 0, len = 0;
	int m;
	printf("\n");
	read(b, &m);
	longest_seq(m, b, &start, &len);
	for (int i = start; i < start + len; ++i)
		printf("%d ", b[i]);
}


int main()
{
	int n, option, p=1;

	while (1)
	{

		printf("\n1. Read a sequence of natural numbers (sequence ended by 0) and determine the number of 0 digits of the product of the read numbers.\n");
		printf("2. Given a vector of numbers, find the longest contiguous subsequence such that the sum of any two consecutive elements is a prime number.\n");
		printf("3. Exit\n");
		printf("Choose your option: ");
		scanf_s("%d", &option);
		if (option == 1)
		{
			fctA();
		}
		if (option == 2)
		{
			fctB();
		}

		
		if (option == 3)
			return 0;
	}

}
//9
//1 3 4 7 2 3 8 9 4






//void decomposition(int n)
//{
//	/*
//	 The function decompose a given number n into its prime factors
//	 It prints the prime factors and their exponentials
//	*/
//	int f = 2;
//	while (n != 1)
//	{
//		int p = 0;
//		while (n % f == 0)
//		{
//			n = n / f;
//			p++;
//		}
//		if (p != 0)
//		{
//			printf("\nThe factor ");
//			printf("%d", f);
//			printf(" has the exponent: ");
//			printf("%d", p);
//		}
//		f++;
//	}
//	return;
//}
//
//void decomposition2(int n, int x)
////The function decomposes the given number into prime factors and prints the exponential of 
////the given prime factor
//{
//	int f = 2;
//	while (n != 1)
//	{
//		int p = 0;
//		while (n % f == 0)
//		{
//			n = n / f;
//			p++;
//		}
//		if (p != 0 && f == x)
//		{
//			printf("\nThe factor ");
//			printf("%d", f);
//			printf(" has the exponent: ");
//			printf("%d", p);
//		}
//		f++;
//	}
//	return;
//}
//if (option == 3)
		//{
		//	int p;
		//	/*printf("Enter the prime number p: ");
		//	scanf_s("%d", &p);*/
		//	printf("\n Enter the natural number n: ");
		//	scanf_s("%d", &n);
		//	decomposition(n);
		//	printf("\n Enter the prime number p: ");
		//	scanf_s("%d", &p);
		//	decomposition2(n, p);
		//}