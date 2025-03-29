import React from "react";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const IncomeChart = ({ data }) => {
  const chartData = {
    labels: data.map((entry) => entry.source), // X-axis labels - source
    datasets: [
      {
        label: "Income Amount (USD)", // Y-axis label
        data: data.map((entry) => entry.amount), // Y-axis data - amount
        backgroundColor: "rgba(54, 162, 235, 0.6)", // Blue color for the bars
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: "Income Source and Amount",
      },
    },
  };

  return (
    <div style={{ width: "70%", margin: "auto", textAlign: "center" }}>
      <h1>Income Chart</h1>
      <Bar data={chartData} options={options} />
    </div>
  );
};

export default IncomeChart;
