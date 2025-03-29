import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import IncomeChart from "./components/IncomeChart";
import ExpensesChart from "./components/ExpensesChart";
import ExpensesPage from "./components/AdminExpensesPage";

const App = () => {
  const [incomeData, setIncomeData] = useState([]);

  useEffect(() => {
    const fetchIncomeData = async () => {
      try {
        const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzQzMTc2NTg3LCJleHAiOjE3NDMxNzk1ODd9.51YvKZIMyqHhAhAlk6IMJWzLFpfWCu1GqDz_e8RW9E0";
        const response = await fetch("http://localhost:9001/api/incomes/abc", {
          headers: {
            Authorization: `Bearer ${token}`, // Include JWT token
          },
        });
        const data = await response.json();
        setIncomeData([
          {
            source: data.source,
            amount: data.amount,
          },
        ]);
      } catch (error) {
        console.error("Error fetching income data:", error);
      }
    };

 //   fetchIncomeData();
  }, []);

  return (
    <Router>
      <div style={{ textAlign: "center", padding: "20px" }}>
        <h1>Financial Dashboard</h1>
        <nav>
          <Link to="/income">📊 View Income Chart</Link> |{" "}
          <Link to="/expenses">💸 View Expenses Chart</Link> |{" "}
          <Link to="/Adminexpenses">📝 Admin Expenses</Link>
        </nav>

        <Routes>
          <Route path="/" element={<div>Welcome to the Financial Dashboard</div>} />
          <Route path="/income" element={<IncomeChart data={incomeData} />} />
          <Route path="/expenses" element={<ExpensesChart />} />
          <Route path="/Adminexpenses" element={<ExpensesPage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
