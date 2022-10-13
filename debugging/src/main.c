#include <stdio.h>
#include <math.h>

double square(double n) {
    double result = pow(n, 2);
    return result;
}

double square_sum(double a, double b)
{
    const double a1 = square(a);
    const double a2 = square(b);

    return a1 + a2;
}

int main() 
{
    const double a =10;
    const double b = 5;
    printf("pow(%f, 2) + pow(%f, 2)=%f", a, b, square_sum(a, b) );
    return 0;
}