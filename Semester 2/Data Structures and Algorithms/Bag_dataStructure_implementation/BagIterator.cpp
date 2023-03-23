#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;

#define empty INT_MIN
BagIterator::BagIterator(const Bag& c): bag(c)
{
	//Implementation
	/*this->currentPos = 0;
	while (this->currentPos < this->bag.capacity && (this->bag.ht.elements[this->currentPos].element == this->bag.null))
		this->currentPos++;
	if (this->currentPos < bag.capacity)
		this->currentElem->element = this->bag.ht.elements[this->currentPos].element;
	else
		this->currentElem->element = this->bag.null;*/
	

	//this->currentElem->element = this->bag.ht.elements->element;
	this->currentPos = 0;
	//this->currentPos = 0;
	//this->first();
}

void BagIterator::first() {
	//Implementation
	/*this->currentElem = this->bag.ht.elements;
	this->currentPos = 0;*/
	//this->currentPos = 0;
	this->currentPos = 0;
	/*while (this->bag.ht.elements[this->currentPos].element == this->bag.null)
		this->currentPos++;*/

	/*this->currentElem = this->bag.ht.elements;
	if (this->currentElem != nullptr)
		this->frequency = this->bag.ht.elements->frequency;*/
}


void BagIterator::next() {
	//Implementation
	if (!valid())
		throw exception();
	/*if (this->currentPos + 1 == this->bag.capacity)
	{
		this->currentElem = this->currentElem->next;
		this->currentPos = 0;

	}
	else
	{
		this->currentPos++;
	}*/

	else {
		this->currentPos++;
	}
	/*while (this->currentPos < bag.capacity && (this->bag.ht.elements[this->currentPos].element == this->bag.null))
		this->currentPos++;*/
	/*if (!this->valid())
		throw exception();
	if (this->frequency == 1)
	{
		this->currentElem = this->currentElem->next;
		if (this->currentElem != nullptr)
		{
			this->frequency = this->currentElem->frequency;
		}
		
	}
    else
		{
			this->frequency--;
		}*/
}


bool BagIterator::valid() const {
	//Implementation
	/*if (this->currentPos == NULL)
		return false;*/
	if (this->currentPos < this->bag.capacity ||this->currentElem!=nullptr)
	{
		return true;
	}
	return false;
}




TElem BagIterator::getCurrent() const
{
	//Implementation
	if (this->currentPos==this->bag.capacity || !valid())
		throw exception();
	// otherwise return the value of the current element
	else {
		return this->bag.ht.elements[this->currentPos].element;
	}
}
