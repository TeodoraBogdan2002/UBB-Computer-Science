#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;

// complexity: tetha(1)
Bag::Bag() {
	//Implementation
	this->ht.elements = nullptr;
	this->capacity = 0;
}

// complexity: tetha(1)
int Bag::hashingFunction(TElem key) const {
	if (key < 0)
		key = this->capacity + key % this->capacity;
	return key % this->capacity;
}

void Bag::resize()
{
	this->capacity = this->capacity * 2;
	node** aux = new node * [this->capacity];
	for (int i = 0; i < this->capacity;i++)
	{
		aux[i] = nullptr;
	}
	for (int i = 0; i < this->capacity / 2;i++)
	{
		node now = ht.elements[i];
		while (now.element != 0)
		{
			int where = this->hashingFunction(now.element);
			node* next = now.next;
			now.next = aux[where];
			*aux[where] = now;
			now = *next;
		}
	}
	delete[] this->ht.elements;
	this->ht.elements = *aux;
	
}

//complexity: O(n) - average
void Bag::add(TElem elem) {
	//Implementation
	node* auxiliaryNode = this->searchElementReturnAddress(elem);
	// if the element does not exist in the bag, we add it by allocating space for a new node, setting its value
	// to elem, its frequency to one and adding it to the beginning of the bag.
	if (auxiliaryNode == nullptr) {
		auxiliaryNode = new node;
		auxiliaryNode->element = elem;
		auxiliaryNode->frequency = 1;
		auxiliaryNode->next = this->ht.elements;
		this->ht.elements = auxiliaryNode;
	}
	else      //otherwise (if we found the element in the bag) we just increase its frequency
		auxiliaryNode->frequency++;
	this->capacity++;
}

//complexity: O(n)
void Bag::addAll(Bag& b)
{
	//int n = b.size();
	int elems[100];
	for (int i = 0; i < b.size();i++)
	{
		elems[i] = b.ht.elements[i].element;

	}
	for (int i = 0; i < b.size(); i++)
	{
		node* auxiliaryNode = this->searchElementReturnAddress(elems[i]);
		if (auxiliaryNode == nullptr) {
			auxiliaryNode = new node;
			auxiliaryNode->element = elems[i];
			auxiliaryNode->frequency = 1;
			auxiliaryNode->next = this->ht.elements;
			this->ht.elements = auxiliaryNode;
		}
		else      //otherwise (if we found the element in the bag) we just increase its frequency
			auxiliaryNode->frequency++;
		this->capacity++;
	}
}

Bag::node* Bag::searchElementReturnAddress(TElem elem) const {
	// we parse the bag using an auxiliaryNode and return a pointer to the node where elem is stored or nullptr if
	// elem is not in the bag
	node* auxiliaryNode = this->ht.elements;
	while (auxiliaryNode != nullptr) {
		if (auxiliaryNode->element == elem)
			return auxiliaryNode;
		auxiliaryNode = auxiliaryNode->next;
	}
	return nullptr;
}

// complexity: O(n)
bool Bag::remove(TElem elem) {
	// we search for the element in the bag. If we don't find it (elemToBeRemoved == nullptr) we return false
	node* elemToBeRemoved, * previousElem;
	this->searchElementAndPrevious(elem, previousElem, elemToBeRemoved);
	if (elemToBeRemoved != nullptr) {
		// if the frequency of the element to be removed is greater than 1, we just have to decrease it to remove one
		// instance of elem
		if (elemToBeRemoved->frequency > 1)
			elemToBeRemoved->frequency--;
		// otherwise we have to remove the node from the list
		else {
			// if the element to be removed is the element of the hash table, we set the element of the hash table to be the next element
			if (elemToBeRemoved == this->ht.elements) {
				ht.elements = ht.elements->next;
			}
			else {
				// otherwise we set the links between previous and next accordingly.
				previousElem->next = elemToBeRemoved->next;
			}
			// then we deallocate the memory for the element from the hash table
			delete elemToBeRemoved;
		}
		this->capacity--;
		return true;        // meaning we removed one instance of elem from the bag
	}
	else return false;    // nothing was removed
}

void Bag::searchElementAndPrevious(TElem elem, Bag::node*& previous, Bag::node*& current) {
	previous = nullptr;
	current = this->ht.elements;
	while (current != nullptr && current->element != elem)
	{
		previous = current;
		current = current->next;
	}
}

// complexity: O(n)
bool Bag::search(TElem elem) const {
	node* auxiliaryElem = this->ht.elements;
	while (auxiliaryElem != nullptr)
	{
		if (auxiliaryElem->element == elem)
			return true;
		auxiliaryElem = auxiliaryElem->next;
	}
	return false;
}

int Bag::nrOccurrences(TElem elem) const {
	node* auxElem = this->searchElementReturnAddress(elem);
	// if the element does not exist, we return 0
	if (auxElem == nullptr)
		return 0;
	// else we return the frequency stored in the node where elem is
	else return auxElem->frequency;
}

//complexity:tetha(1)
int Bag::size() const {
	return this->capacity;
}

//complexity:tetha(1)
bool Bag::isEmpty() const {
	return this->capacity == 0;
}

//complexity:tetha(1)
BagIterator Bag::iterator() const {
	return BagIterator(*this);
}


Bag::~Bag() {
	node* auxiliaryNode = this->ht.elements, * nextNode;
	while (auxiliaryNode != nullptr)
	{
		nextNode = auxiliaryNode->next;
		delete auxiliaryNode;
		auxiliaryNode = nextNode;
	}
}



/*bool returnValue = false;
	int current = this->hashingFunction(elem);
	int prev = -1;
	while (current != -1 && this->ht.elements[current].element != elem)
	{
		prev = current;
		current = this->ht.elements->next[current].element;

	}
	if (current == -1)
	{
		returnValue = false;
	}
	else
	{
		returnValue = true;
		bool over = false;
		do
		{
			int position = this->ht.elements->next[current].element;
			int prevPosition = current;
			while (position != -1 && this->hashingFunction(this->ht.elements[position].element) != current)
			{
				prevPosition = position;
				position = this->ht.elements->next[position].element;

			}
			if (position == -1)
			{
				over = true;
			}
			else
			{
				this->ht.elements[current] = this->ht.elements[position];
				prev = prevPosition;
				current = position;
			}

		} while (!over);
		if (prev == -1)
		{
			int idx = 0;
			while (idx < this->capacity && prev == -1)
			{
					if (this->ht.elements->next[idx].element == current)
					{
						prev = idx;
					}
					else
					{
						idx = idx + 1;
					}
			}
		}
		if (prev != -1)
		{
			this->ht.elements->next[prev] = this->ht.elements->next[current];

		}
		this->capacity--;
		this->ht.elements[current].element=NULL_TELEM;
		this->ht.elements->next[current].element = -1;

	}
	return returnValue;*/