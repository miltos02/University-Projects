#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>
#include "theater-booking-res.h"


int seat_layout[30][10]; //array of the theater seating
int stats[3]; //array of the average time variables
int Ncust;
int seedp;
pthread_mutex_t telephonists; //mutex of telephone operators
pthread_mutex_t cashiers; //mutex of cashiers
pthread_mutex_t layout; //mutex of the theater
pthread_mutex_t bank_account; //mutex of profit
pthread_mutex_t statistics; //mutex of statistics
pthread_mutex_t print_screen; //mutex of screen output
pthread_mutex_t wait; //mutex of waiting
pthread_mutex_t service; //mutex of service
pthread_cond_t cond1; 
pthread_cond_t cond2;
double total_service = 0; //total time of customer service
double total_wait = 0; //total time of customer wait
int available_telephonists = Ntel; //available telephone operators
int available_cashiers = Ncash; //available cashiers
int account = 0; //profit

int main(int argc, char ** argv)
{ 
    int *id; //id of clients
    int rc; //returning code
    int j; int i;
    char zone1; 
    double average_waiting_time; //average waiting time
    double average_servicing_time; //average waiting time
    float percentage1; //transaction statistics
    float percentage2; //transaction statistics
    float percentage3; //transaction statistics
    pthread_t *threads;
    if(argc == 3) //check that 2 parameters are given
    {
        Ncust = atoi(argv[1]);
        seedp = atoi(argv[2]);
    }
    else
    {
        printf("Two parameters should be given.\n");
        return(-1);
    }
    for (i = 0; i < 3; i++)
    {
        stats[i] = 0;
    }

    //mutex creation
    pthread_mutex_init(&layout, NULL);
    pthread_mutex_init(&statistics, NULL); 
    pthread_mutex_init(&bank_account, NULL);
    pthread_mutex_init(&print_screen, NULL);
    pthread_mutex_init(&cashiers, NULL);
    pthread_mutex_init(&telephonists, NULL);
    pthread_mutex_init(&service, NULL);
    pthread_mutex_init(&wait, NULL);
    pthread_cond_init(&cond1, NULL);
    pthread_cond_init(&cond2, NULL);

    id = (int *)malloc(Ncust * sizeof(int));
    threads = (pthread_t*)malloc(Ncust * sizeof(pthread_t));

    for(i=0; i<(NzoneA+NzoneB); i++) //seat array creation
    {
        for(j=0; j<Nseat; j++)
        {
            seat_layout[i][j]=0;
        }
    }
    for (i = 0; i < Ncust; i++) //thread array creation
    {
        id[i] = i+1;
        seedp = seedp + id[i];
        if (i > 0)
        {
            sleep(rand_r(&seedp) % (treshigh-treslow+1) + treslow);
        }
    	rc = pthread_create(&threads[i], NULL, customer, &id[i]);
        if(rc)
        {
            printf("Error:%d\n",rc);
            return -1;
        }
    }
    for (i = 0; i < Ncust; i++) //joining the threads
    {
       rc = pthread_join(threads[i], NULL);
       if(rc)
       {
            printf("Error:%d\n",rc);
            return -1;
       }
    }
    for(i=0; i<(NzoneA+NzoneB); i++) //seat array printing
    {
        for(j=0; j<Nseat; j++)
        {
            if(i<NzoneA)
            {
                zone1 = 'A';
                printf("Zone %c / Line %d / Seat %d / Customer %d\n", zone1, i+1, j+1, seat_layout[i][j]);
            }
            else
            {
                zone1 = 'B';
                printf("Zone %c / Line %d / Seat %d / Customer %d\n", zone1, i+1, j+1, seat_layout[i][j]);
            }
        }
    }
    printf("Total profit = %d\n", account);
    if(Ncust != 0) // results printing
    {
        percentage1 = (float)stats[0]/Ncust * 100.0;
        percentage2 = (float)stats[1]/Ncust * 100.0;
        percentage3 = (float)stats[2]/Ncust * 100.0;
        average_waiting_time = total_wait/Ncust;
        average_servicing_time = total_service/Ncust;
        printf("Percentage of successful bookings = %.2f %%\n", percentage1);
        printf("Percentage of unsuccessful bookings due to lack of seats = %.2f %%\n", percentage2);       
        printf("Percentage of unsuccessful bookings due to rejected transaction = %.2f %%\n", percentage3);
        printf("The average waiting time for customers is: %.2f sec\n", average_waiting_time);
        printf("The average servicing time for customers is: %.2f sec\n", average_servicing_time);
    }
    else
    {
        printf("No transaction was completed.\n");
    }

    free(id);
    free(threads);

    //destroying the created threads
    pthread_mutex_destroy(&layout);
    pthread_mutex_destroy(&statistics);
    pthread_mutex_destroy(&print_screen);
    pthread_mutex_destroy(&bank_account);
    pthread_mutex_destroy(&telephonists);
    pthread_mutex_destroy(&cashiers);
    pthread_mutex_destroy(&service);
    pthread_mutex_destroy(&wait);
    pthread_cond_destroy(&cond1);
    pthread_cond_destroy(&cond2);

    return 0;
}

void *customer(void *identity)
{
    struct timespec start_waiting, finish_waiting, start_servicing, finish_servicing;
    char possible1[10]; //array of possibilities
    char exchange; //exchange successful(S) or unsuccessful/failed(F)
    char possible2[10]; //array of possibilities
    char zone; 
    int people; //number of seats the client wants
    int m; int n; 
    int j; int i; int row;
    int k; int w; int line;
    int possibility; 
    int rc; //returning code
    int seed;
    int id = *(int*)identity;
    seed = seedp + id;
    int amount = 0; //how much will the client pay

    if (clock_gettime(CLOCK_REALTIME, &start_servicing) == -1) //start of total servicing time
    {
        perror("clock gettime");
        exit(EXIT_FAILURE);
    }

    rc = pthread_mutex_lock(&telephonists);
    if(rc)
    {
        printf("Error:%d\n",rc);
        pthread_exit(NULL);
    }
    if( clock_gettime( CLOCK_REALTIME, &start_waiting) == -1 ) //start of waiting time for a telephone operator
    {
        perror( "clock gettime" );
        exit( EXIT_FAILURE );
    }
    while (available_telephonists == 0) //while there aren't available telephone operators: the client waits
    {
        rc = pthread_cond_wait(&cond1, &telephonists);
        if(rc)
        {
            printf("Error:%d\n",rc);
            pthread_exit(NULL);
        }
    }
    available_telephonists--;
    if( clock_gettime( CLOCK_REALTIME, &finish_waiting) == -1 ) //waiting time for a telephone operator stops
    {
        perror( "clock gettime" );            
        exit( EXIT_FAILURE );
    }
    rc = pthread_mutex_unlock(&telephonists);
    if(rc)
    {
        printf("Error:%d\n",rc);
        pthread_exit(NULL);
    }
    rc = pthread_mutex_lock(&wait);
    if(rc)
    {
        printf("Error:%d\n",rc);
        pthread_exit(NULL);
    }
    total_wait += finish_waiting.tv_sec - start_waiting.tv_sec; //updating time for a telephone operator
    rc = pthread_mutex_unlock(&wait);
    if(rc)
    {
        printf("Error:%d\n",rc);
        pthread_exit(NULL);
    }
    for (i = 0; i < 10; i++) //creating possibility of the client wanting zone A or B
    {
        if (i < (PzoneA * 10))
        {
            possible1[i] = 'A';
        }
        else
        {
            possible1[i] = 'B';
        }
    }
    possibility = rand_r(&seed) % 10;
    zone = possible1[possibility];
    people = rand_r(&seed) % (Nseathigh - Nseatlow +1) + Nseatlow;

    sleep(rand_r(&seed) % (tseathigh-tseatlow+1) + tseatlow);

    rc = pthread_mutex_lock(&layout);
    if (rc)
    {
        printf("Error:%d\n", rc);
        pthread_exit(NULL);
    }
    if (zone == 'A') 
    {
        k = NzoneA;
        w = 0;
    }
    else
    {
        k = NzoneB+10;
        w = 10;
    }
    for (i = w; i < k; i++) //searching continuous available seats according to the number of tickets the client wants
    {
        m = 0;
        for (j = 0; j < Nseat; j++)
        {
            if (seat_layout[i][j] == 0)
            {
                m++;
                if (m == people)
                {
                    line = i;
                    row = j - m + 1;
                    for (n = row; n < row + people; n++)
                    {
                        seat_layout[line][n] = id; //if the necessary seats are found we put the ids in them
                    }
                    break;
                }
            }
            else
            {
                m = 0;
            }
        }
        if (m == people)
        	break;
    }
    rc = pthread_mutex_unlock(&layout);
    if (rc)
    {
        printf("Error:%d\n", rc);
        pthread_exit(NULL);
    }

    if (m == people) //if seats are found
    {
        if (zone == 'A')
        {
            amount = CzoneA * people;
        }
        else
        {
            amount = CzoneB * people;
        }
        rc = pthread_mutex_lock(&telephonists);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        available_telephonists++;
        rc = pthread_cond_signal(&cond1);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_unlock(&telephonists);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&cashiers);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        if (clock_gettime(CLOCK_REALTIME, &start_waiting) == -1) //wait for a cashier
        {
            perror("clock gettime");
            exit(EXIT_FAILURE);
        }
        while (available_cashiers == 0) //while there are no available cashiers the client waits
        {
            rc = pthread_cond_wait(&cond2, &cashiers);
            if (rc)
            {
                printf("Error:%d\n", rc);
                pthread_exit(NULL);
            }
        }
        if (clock_gettime(CLOCK_REALTIME, &finish_waiting) == -1) //waiting for available cashiers stops
        {
            perror("clock gettime");
            exit(EXIT_FAILURE);
        }
        available_cashiers--;
        rc = pthread_mutex_unlock(&cashiers);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&wait);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        total_wait += finish_waiting.tv_sec - start_waiting.tv_sec; //updating total waiting time
        rc = pthread_mutex_unlock(&wait);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
    }
    else //if there weren't available seats
    {
        rc = pthread_mutex_lock(&print_screen);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        printf("%d : Booking failed because there weren't available seats.\n", id);
        rc = pthread_mutex_unlock(&print_screen);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }

        rc = pthread_mutex_lock(&statistics);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        stats[1] = stats[1] + 1; //updating statistic of failing booking because of lack of seats
        rc = pthread_mutex_unlock(&statistics);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&telephonists);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        available_telephonists++;
        rc = pthread_cond_signal(&cond1);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_unlock(&telephonists);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        if (clock_gettime(CLOCK_REALTIME, &finish_servicing) == -1) //servicing time stops
        {
            perror("clock gettime");
            exit(EXIT_FAILURE);
        }
        rc = pthread_mutex_lock(&service);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        total_service += finish_servicing.tv_sec - start_servicing.tv_sec; //updating total servicing time
        rc = pthread_mutex_unlock(&service);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        pthread_exit(NULL);
    }
    sleep((rand_r(&seed) % (tcashhigh - tcashlow +1)) + tcashlow);
    for (i = 0; i < 10; i++) //creating array of possibility of failing transaction card unacceptance
    {
        if (i < (Pcardsuccess * 10))
        {
            possible2[i] = 'S'; //Successful
        }
        else
        {
            possible2[i] = 'F'; //Failed
        }
    }
    possibility = rand_r(&seed) % 10;
    exchange = possible2[possibility];
    if (exchange == 'S') //successful transaction
    {
        rc = pthread_mutex_lock(&bank_account);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        account += amount;
        rc = pthread_mutex_unlock(&bank_account);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&print_screen);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        printf("%d : The booking was completed successfully. Your seats are in zone %c, row %d, number ", id, zone, line + 1);
        for (j = row; j < row + people; j++)
        {
            if (j < row + people - 1) 
            {
                printf("%d, ", j+1);
            }
            else
            {
                printf("%d ", j+1);
            }
        }
        printf("and the cost of the transaction is %d euros.\n", amount);
        rc = pthread_mutex_unlock(&print_screen);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        
        rc = pthread_mutex_lock(&statistics);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        stats[0] = stats[0] + 1; //counter of successful bookings
        rc = pthread_mutex_unlock(&statistics);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&cashiers);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        available_cashiers++;
        rc = pthread_cond_signal(&cond2);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_unlock(&cashiers);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
    }
    else //booking failure
    {
        rc = pthread_mutex_lock(&print_screen);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        printf("%d : The reservation failed because the transaction with the credit card was not accepted.\n", id);
        
        rc = pthread_mutex_unlock(&print_screen);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        
        rc = pthread_mutex_lock(&statistics);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        stats[2] = stats[2] + 1; //counter of failure because of failed transaction
        rc = pthread_mutex_unlock(&statistics);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&layout);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        for (j = row; j < row + people; j++)
        {
            seat_layout[line][j] = 0; //making temporarily unavailable seats into available
        }
        rc = pthread_mutex_unlock(&layout);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_lock(&cashiers);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        available_cashiers++;
        rc = pthread_cond_signal(&cond2);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
        rc = pthread_mutex_unlock(&cashiers);
        if (rc)
        {
            printf("Error:%d\n", rc);
            pthread_exit(NULL);
        }
    }
    if (clock_gettime(CLOCK_REALTIME, &finish_servicing) == -1) //stopping service time
    {
        perror("clock gettime");
        exit(EXIT_FAILURE);
    }
    rc = pthread_mutex_lock(&service);
    if (rc)
    {
        printf("Error:%d\n", rc);
        pthread_exit(NULL);
    }
    total_service += finish_servicing.tv_sec - start_servicing.tv_sec; //adding servicing time to total servicing time
    rc = pthread_mutex_unlock(&service);
    if (rc)
    {
        printf("Error:%d\n", rc);
        pthread_exit(NULL);
    }
    
    pthread_exit(NULL);
}