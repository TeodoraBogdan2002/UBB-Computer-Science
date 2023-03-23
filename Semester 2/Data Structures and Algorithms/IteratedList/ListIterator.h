#pragma once
#include "IteratedList.h"

//DO NOT CHANGE THIS PART
class IteratedList;
typedef int TElem;

class ListIterator {
	friend class IteratedList;
private:
	//Representation
	IteratedList::PNode currentElement;

	//DO NOT CHANGE THIS PART
	const IteratedList& list;
	ListIterator(const IteratedList& lista);
public:
	friend class List;
	void first();
	void next();
	bool valid() const;
	TElem getCurrent() const;

};


