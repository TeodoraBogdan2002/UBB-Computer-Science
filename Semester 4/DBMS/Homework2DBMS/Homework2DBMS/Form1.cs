using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

using System.Configuration;
using System.Collections.Specialized;


namespace Homework2DBMS
{
    public partial class Form1 : Form
    {
        SqlConnection con;
        SqlDataAdapter daContract;
        SqlDataAdapter daEmployee;
        DataSet ds;
        BindingSource bsContract;
        BindingSource bsEmployee;


        SqlCommandBuilder cmdBuilder;
        SqlCommandBuilder cmdBuilder2;



        public Form1()
        {
            InitializeComponent();
            FillData();
            this.dataGridView2.CellValidating += new DataGridViewCellValidatingEventHandler(dataGridView2_CellValidating);

        }

        private string getDatabase()
        {
            return ConfigurationManager.AppSettings["Database"].ToString();
        }

        private string getParentTableName()
        {
            return ConfigurationManager.AppSettings["ParentTableName"].ToString();
        }

        private string getChildTableName()
        {
            return ConfigurationManager.AppSettings["ChildTableName"].ToString();
        }

        private string getParentSelectQuery()
        {
            return ConfigurationManager.AppSettings["ParentSelectQuery"].ToString();
        }

        private string getChildSelectQuery()
        {
            return ConfigurationManager.AppSettings["ChildSelectQuery"].ToString();
        }

        private string getParentReferencedKey()
        {
            return ConfigurationManager.AppSettings["ParentReferencedKey"].ToString();
        }

        private string getChildForeignKey()
        {
            return ConfigurationManager.AppSettings["ChildForeignKey"].ToString();
        }

        private string getParentSelectionQuery()
        {
            return ConfigurationManager.AppSettings["ParentSelectionQuery"].ToString();
        }

        void FillData() // fill the form with the data from the database
        {
            con = new SqlConnection(getConnectionString());

            //queryContract = "select * from contract_em";
            //queryEmployee = "select * from employees";

            //SqlDataAdapter, DataSet
            daContract = new SqlDataAdapter(getParentSelectQuery(), con);
            daEmployee = new SqlDataAdapter(getChildSelectQuery(), con);
            ds = new DataSet();
            daContract.Fill(ds, getParentTableName()); //Parent
            daEmployee.Fill(ds, getChildTableName()); //Child

            //fill in the Insert, update, delete commands
            cmdBuilder = new SqlCommandBuilder(daEmployee);
            cmdBuilder2 = new SqlCommandBuilder(daContract);

            //parent then child

            //add the parent-child relationship to the daya set ds
            ds.Relations.Add("Contract_Employee",
                ds.Tables[getParentTableName()].Columns[getParentReferencedKey()],
                ds.Tables[getChildTableName()].Columns[getChildForeignKey()]);

            //fill in the data in the gridView
            //Method1


            //Method2: using the data binding
            bsContract = new BindingSource();
            bsContract.DataSource = ds.Tables[getParentTableName()];
            bsEmployee = new BindingSource(bsContract, "Contract_Employee");

            this.dataGridView1.DataSource = bsContract;
            this.dataGridView2.DataSource = bsEmployee;

        }

        string getConnectionString()
        {
            return "Data Source=DESKTOP-8DH3TJB\\SQL;" +
                            "Initial Catalog = japanese_r; Integrated Security=SSPI;";
        }

        private void dataGridView2_CellValidating(object sender, DataGridViewCellValidatingEventArgs e)
        {
            if (e.ColumnIndex == 0) // ID column
            {
                int id;
                if (!int.TryParse(e.FormattedValue.ToString(), out id))
                {
                    e.Cancel = true;
                    MessageBox.Show("The ID must be a valid integer.");
                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daEmployee.Update(ds, getChildTableName());
                daContract.Update(ds, getParentTableName());
                MessageBox.Show("Data uploaded successfully!");

            }
            catch (SqlException ex)
            {

                if (ex.Number == 2627) // validates the primary key; checks if it appears already or not
                {
                    MessageBox.Show("A child table with the same ID already exists");
                }
                else if (ex.Message.Contains("Cannot insert the value NULL into column"))
                {
                    MessageBox.Show("The ID field cannot be epmty. Please fill in the required field.");
                }
                else
                {
                    MessageBox.Show("An error occured while updating the data: " + ex.Message);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("An error occured while updating the data: " + ex.Message);
            }

        }
        private void refreshButton_Click(object sender, EventArgs e)
        {
            ds = new DataSet();

            daContract.Fill(ds, getParentTableName());
            daEmployee.Fill(ds, getChildTableName());
        }

        //add exceptions and validations
    }
}