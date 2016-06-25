#include "red_black_tree_gui.h"
#include "ui_red_black_tree_gui.h"

Red_Black_Tree_GUI::Red_Black_Tree_GUI(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Red_Black_Tree_GUI)
{
    ui->setupUi(this);
}

Red_Black_Tree_GUI::~Red_Black_Tree_GUI()
{
    delete ui;
}
