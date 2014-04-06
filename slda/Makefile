CC = g++
LDFLAGS = -lgsl -lm -lgslcblas
IFLAGS ?= -I/home/mz342/library/gsl-1.16/
LFLAGS ?= -L/home/mz342/library/gsl-1.16/

LSOURCE = main.cpp corpus.cpp slda.cpp utils.cpp opt.cpp
LHEADER = corpus.h slda.h utils.h opt.h settings.h

# target: slda
# $(LSOURCE) $(LHEADER) source
#

slda: $(LSOURCE) $(LHEADER)
	  $(CC) $(LFLAGS) $(LSOURCE) -o $@ $(LDFLAGS) $(IFLAGS)
#$@ file name of the target of the rule

clean:
	-rm -f *.o slda
