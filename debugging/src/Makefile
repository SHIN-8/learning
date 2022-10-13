CC            = gcc
CFLAGS        = -O0 -g -v -Wall -I/usr/local/include
LDFLAGS       = -lm
OBJS          = main.o
PROGRAM       = main

# make all
all: $(PROGRAM)
$(PROGRAM): $(OBJS)
	$(CC) $(OBJS) $(LDFLAGS) $(LIBS) -o $(PROGRAM)

# clean
clean:
	rm -f *.o $(PROGRAM)

