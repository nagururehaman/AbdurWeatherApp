#include <stdio.h>
//function declaration
void sort(int numbers[], int n);

// function implemented in pseudocode 
double sortAndFindMedian(int numbers[], int n) {
    sort(numbers, n);
    if (n % 2 == 0)
        return (double)(numbers[n / 2 - 1] + numbers[n / 2]) / 2;
    else
        return (double)numbers[n / 2];
}

//function for soting the array numbers , I have used Selection Sort
void sort(int numbers[], int n) {
    for (int i = 0; i < n - 1; i++) {
        int min = i;
        for (int j = i + 1; j < n; j++) {
            if (numbers[j] < numbers[min]) {
                min = j;
            }
        }
        int temp = numbers[i];
        numbers[i] = numbers[min];
        numbers[min] = temp;
    }
}

//Main function and user interface to get the input from user for numbers of array
int main() {
    int n;
    printf("Enter how many elements you want to store: ");
    scanf("%d", &n);
    int numbers[n];
    printf("Enter the %d elements:\n", n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &numbers[i]);
    }
    printf("Median: %.2f\n", sortAndFindMedian(numbers, n));

    return 0;
}


