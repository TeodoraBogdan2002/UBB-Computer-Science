#include <exception>
#include "ListIterator.h"
#include "IteratedList.h"
using namespace std;

//struct IteratedList::SLLNode {
//	TElem info;
//	PNode next;
//	//SLLNode(TElem e, PNode n);
//};
IteratedList::SLLNode::SLLNode(TElem e, PNode n) {
	info = e;
	next = n;
}

IteratedList::IteratedList() {
	//Implementation
	
	head = nullptr;
}

int IteratedList::size() const {
	//Implementation
	
	//    Theta(1)
	if (this->isEmpty())
		return 0;
	int count = 0;
	PNode current = head;
	while (current->next!=NULL)
	{
		count++;
		current = current->next;
	}
	return count+1;
}



bool IteratedList::isEmpty() const {
	//Implementation
	
	//     Theta(1)
	if (this->head == NULL)
		return true;
	return false;
}

ListIterator IteratedList::first() const {
	return ListIterator(*this);
}

TElem IteratedList::getElement(ListIterator pos) const {
	//Implementation
	
	//     Theta(1)
	if (pos.currentElement == nullptr)
		throw exception();
	return pos.currentElement->info;
	
}

void IteratedList::empty()
{
	//this->head = NULL;
	PNode x = head->next;
	while (x != NULL)
	{
		head->next = x->next;
		x->next = NULL;
		//head = head->next;
		free(x);
		x = head->next;
	}
}

TElem IteratedList::remove(ListIterator& pos) {
	//Implementation
	
	//      Theta(n)
	PNode pn;
	if (pos.valid() == false)
	{
		throw std::exception::exception();
	}
	else
	{
		TElem return_elem;
		if (this->head == pos.currentElement)
		{
			return_elem = head->info;
			head = head->next;
		}
		else
		{
			PNode i = head;
			while (i->next != NULL && i->next != pos.currentElement)
			{
				i = i->next;
			}
			return_elem = pos.currentElement->info;
			if (pos.currentElement->next == NULL)
			{
				i->next = NULL;
				ListIterator list = ListIterator(*this);
				list.first();
			}
			else
			{
				ListIterator list = ListIterator(*this);
				while (i->next != NULL && i->next != pos.currentElement)
				{
					i = i->next;
					list.next();
				}
				list.next();
				i->next = pos.currentElement->next;
			}
		}
		return return_elem;
	}
}



ListIterator IteratedList::search(TElem e) const {
	//Implementation
	//     O(n)
	PNode current = this->head;
	ListIterator list = ListIterator(*this);
	while (current != NULL && current->info!=e)
	{
		current = current->next;
		list.next();
		/*if(current->info == e)
			return ListIterator(*this);*/
	}
	if (current != NULL)
		return list;
	
}

TElem IteratedList::setElement(ListIterator pos, TElem e) {
	//Implementation
	//       O(1)
	PNode pn;
	pn = pos.currentElement;
	if (pn == nullptr)
	{
		throw std::exception::exception();
	}
	else
	{
		TElem old;
		old = pn->info;
		pn->info = e;
		return old;
	}
	return NULL_TELEM;
}

void IteratedList::addToPosition(ListIterator& pos, TElem e) {
	//Implementation
	
	//       theta(n)
	if (!pos.valid())
		throw exception();
	PNode newElem = new SLLNode(e, nullptr);
	
	if (this->isEmpty())
	{
		newElem->info = e;
		newElem->next = head;
		head = newElem;
		pos.currentElement = head;
	}
	else
	{
		PNode next = pos.currentElement->next;
		pos.currentElement->next = newElem;
		newElem->next = next;
		pos.currentElement = newElem;
	}
	/*PNode prevNode = pos.currentElement - 1;
	PNode newn = new SLLNode(e, nullptr);
	newn->next = prevNode->next;
	prevNode->next = newn;*/

}

void IteratedList::addToEnd(TElem e) {
	//Implementation
	PNode newNode = new SLLNode(e, nullptr);
	newNode->info = e;
	if (head == nullptr)
	{
		newNode->next = head;
		head = newNode;
	}
	else
	{
		PNode pn = head;
		while (pn->next != NULL)
		{
			pn = pn->next;
		}
		pn->next = newNode;
	}
}

void IteratedList::addToBeginning(TElem e)
{
	PNode pn = new SLLNode(e, nullptr);
	pn->next = head;
	head = pn;
}

IteratedList::~IteratedList() {
	//Implementation
	while (head != nullptr)
	{
		PNode p = head;
		head = head->next;
		delete p;
	}
}
