import React, { useEffect, useState } from "react";
import axios from "axios";
import html2pdf from "html2pdf.js";

const ExpensesPage = () => {
  const [expenses, setExpenses] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    try {
      const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzQzMTc5NTU4LCJleHAiOjE3NDMxODI1NTh9.GHr10nNNQSKsVJsTZGoM9tkVhJn1BbQUC8nmuZQp2bY";
      if (!token) {
        setError("No authentication token found.");
        return;
      }

      const response = await axios.get("http://localhost:9001/api/expense", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setExpenses(response.data);
    } catch (error) {
      console.error("Error fetching expenses:", error);

      // Check for JWT-related errors
      if (error.response && error.response.status === 401) {
        setError("Unauthorized: Invalid or expired token.");
      } else {
        setError("Failed to fetch expenses. Please check your authentication.");
      }
    }
  };

  const generatePDF = () => {
    const element = document.getElementById("expense-table");
    const opt = {
      margin: 1,
      filename: "expense_report.pdf",
      image: { type: "jpeg", quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: "in", format: "letter", orientation: "portrait" },
    };

    html2pdf().from(element).set(opt).save();
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Expense Tracker</h1>
      {error && <p style={styles.error}>{error}</p>}
      <div style={styles.card}>
        <button style={styles.downloadButton} onClick={generatePDF}>
          Generate PDF
        </button>
      </div>
      <div id="expense-table" style={styles.tableWrapper}>
        <ExpenseTable expenses={expenses} />
      </div>
    </div>
  );
};

const ExpenseTable = ({ expenses }) => {
  return (
    <table style={styles.table}>
      <thead>
        <tr>
          <th>National ID</th>
          <th>Name</th>
          <th>Category</th>
          <th>Amount</th>
          <th>Currency</th>
          <th>Expense Date</th>
          <th>Payment Method</th>
          <th>Recurring</th>
          <th>Notes</th>
        </tr>
      </thead>
      <tbody>
        {expenses.map((expense) => (
          <tr key={expense.id}>
            <td>{expense.national_id}</td>
            <td>{expense.name_of_expense}</td>
            <td>{expense.category}</td>
            <td style={styles.amountCell}>{expense.amount}</td>
            <td>{expense.currency}</td>
            <td>{new Date(expense.expenseDate).toLocaleString()}</td>
            <td>{expense.paymentMethod}</td>
            <td style={styles.recurringCell}>
              {expense.recurring ? "✔ Yes" : "✖ No"}
            </td>
            <td>{expense.notes}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

const styles = {
  container: {
    fontFamily: "'Roboto', sans-serif",
    textAlign: "center",
    padding: "40px 20px",
    background: "linear-gradient(135deg, #00c6ff, #0072ff)",
    minHeight: "100vh",
  },
  title: {
    color: "#fff",
    fontSize: "36px",
    marginBottom: "20px",
    textShadow: "2px 2px 5px rgba(0,0,0,0.3)",
  },
  error: {
    color: "red",
    marginBottom: "20px",
  },
  card: {
    marginBottom: "30px",
  },
  downloadButton: {
    padding: "12px 24px",
    backgroundColor: "#2a9d8f",
    color: "#fff",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "16px",
    boxShadow: "0px 5px 15px rgba(0,0,0,0.1)",
    transition: "all 0.3s ease",
  },
  tableWrapper: {
    backgroundColor: "#fff",
    borderRadius: "10px",
    boxShadow: "0px 5px 15px rgba(0,0,0,0.1)",
    padding: "20px",
    overflowX: "auto",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
    marginBottom: "20px",
  },
  amountCell: {
    color: "#e63946",
    fontWeight: "bold",
  },
  recurringCell: {
    color: "#2a9d8f",
    fontWeight: "bold",
  },
};

export default ExpensesPage;
