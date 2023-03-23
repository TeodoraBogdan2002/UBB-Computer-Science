#include "ListIterator.h"
#include "IteratedList.h"
#include <exception>
using namespace std;


ListIterator::ListIterator(const IteratedList& l) : list(l) {
	//Implementation
	currentElement = list.head;
}

void ListIterator::first() {
	//Implementation
	currentElement = list.head;
}

void ListIterator::next() {
	//Implementation
	if (currentElement == nullptr)
		throw std::exception::exception();
	currentElement = currentElement->next;
}

bool ListIterator::valid() const {
	//Implementation
	if (currentElement != nullptr)
		return true;
	return  false;
}

TElem ListIterator::getCurrent() const {
	//Implementation
	if (currentElement == nullptr)
	{
		throw exception();
		
	}
	else
	{
		return currentElement->info;
	}
	return -1;
}



