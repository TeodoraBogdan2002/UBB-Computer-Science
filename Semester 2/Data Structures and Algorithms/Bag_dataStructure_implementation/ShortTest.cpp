#include "ShortTest.h"
#include <assert.h>
#include <stdio.h>
#include "Bag.h"
#include "BagIterator.h"
#include <iostream>


using namespace std;

void testAll() { 
	Bag b;
	assert(b.isEmpty() == true);
	assert(b.size() == 0); 
	b.add(5);
	b.add(1);
	b.add(10);
	b.add(7);
	b.add(1);
	b.add(11);
	b.add(-3);
	assert(b.size() == 7);
	assert(b.search(10) == true);
	assert(b.search(16) == false);
	assert(b.nrOccurrences(1) == 2);
	assert(b.nrOccurrences(7) == 1);
	assert(b.remove(1) == true);
	assert(b.remove(6) == false);
	assert(b.size() == 6);
	assert(b.nrOccurrences(1) == 1);
	BagIterator it = b.iterator();
	it.first();
	while (it.valid()) {
		TElem e = it.getCurrent();
		it.next();
	}
	cout << "d";
	cout << "dd";

	Bag xc;
	assert(xc.isEmpty() == true);
	assert(xc.size() == 0);

	xc.add(5);
	xc.add(1);
	xc.add(10);
	xc.add(7);
	b.addAll(xc);
	assert(b.size() == 10);
}
