#include <iostream>



struct Node {
    int data = 50;
    struct Node *next;
};

struct Node *insertInEmpty(struct Node *head, int new_data)
{
    // if head is not null then list is not empty, so return
    if (head != NULL)
        return head;

    // allocate memory for node
    struct Node *temp = new Node;

    // Assign the data.
    temp -> data = new_data;
    head = temp;

    // Create the link.
    head->next = head;

    return head;
}




int main() {
    struct Node *head = NULL;
    head = insertInEmpty(head, 10);
    Node p;

    return 0;
}
