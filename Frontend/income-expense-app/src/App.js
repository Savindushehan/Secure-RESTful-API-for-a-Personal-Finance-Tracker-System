import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import IncomeChart from "./components/IncomeChart";
import ExpenseChart from "./components/ExpenseChart"; // Import ExpenseChart

const App = () => {
  const [incomeData, setIncomeData] = useState([]);
  const [expenseData, setExpenseData] = useState([]);

  useEffect(() => {
    // Fetching income data
    fetch("http://localhost:9001/api/incomes/abc") // Replace with your API endpoint
      .then((response) => response.json())
      .then((data) => {
        console.log("Income Data:", data); // Log the income data to the console
        setIncomeData([
          {
            source: data.source,
            amount: data.amount,
          },
        ]);
      })
      .catch((error) => console.error("Error fetching income data:", error));

    // Fetching expense data
    fetch("http://localhost:9001/api/expense/123456789V1") // Replace with your API endpoint for expenses
      .then((response) => response.json())
      .then((data) => {
        console.log("Expense Data:", data); // Log the expense data to the console
        setExpenseData(data);  // Pass the raw data
      })
      .catch((error) => console.error("Error fetching expense data:", error));
  }, []);

  return (
    <Router>
      <div style={{ textAlign: "center", padding: "20px" }}>
        <h1>Financial Dashboard</h1>
        <nav>
          <Link to="/income">📊 View Income Chart</Link> | 
          <Link to="/expense">💸 View Expense Chart</Link>
        </nav>

        <Routes>
          <Route path="/" element={<div>Welcome to the Financial Dashboard</div>} />
          <Route path="/income" element={<IncomeChart data={incomeData} />} />
          <Route path="/expense" element={<ExpenseChart data={expenseData} />} /> {/* Add expense route */}
        </Routes>
      </div>
    </Router>
  );
};

export default App;
