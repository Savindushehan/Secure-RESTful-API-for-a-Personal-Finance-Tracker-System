import React, { useState, useEffect } from "react";
import { View, Text } from "react-native";
import { VictoryBar, VictoryPie, VictoryChart, VictoryAxis, VictoryTheme } from "victory-native";
import axios from "axios";

const ExpenseCharts = () => {
  const [expenseData, setExpenseData] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:9001/api/expense/123456789V1")
      .then((response) => setExpenseData(response.data))
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  // Group data by category for Bar Chart
  const categoryData = expenseData.reduce((acc, expense) => {
    const existingCategory = acc.find(item => item.category === expense.category);
    if (existingCategory) {
      existingCategory.amount += expense.amount;
    } else {
      acc.push({ category: expense.category, amount: expense.amount });
    }
    return acc;
  }, []);

  // Group data by expenseDate for Pie Chart
  const dateData = expenseData.reduce((acc, expense) => {
    const existingDate = acc.find(item => item.expenseDate === expense.expenseDate);
    if (existingDate) {
      existingDate.amount += expense.amount;
    } else {
      acc.push({ expenseDate: expense.expenseDate, amount: expense.amount });
    }
    return acc;
  }, []);

  return (
    <View style={{ flex: 1, alignItems: "center", justifyContent: "center", padding: 20 }}>
      <Text style={{ fontSize: 18, fontWeight: "bold", marginBottom: 10 }}>Category Wise Expense (Bar Chart)</Text>

      <VictoryChart theme={VictoryTheme.material} domainPadding={20}>
        <VictoryAxis dependentAxis />
        <VictoryAxis tickFormat={(tick) => tick} />
        <VictoryBar
          data={categoryData}
          x="category"
          y="amount"
          style={{ data: { fill: "#8884d8" } }}
        />
      </VictoryChart>

      <Text style={{ fontSize: 18, fontWeight: "bold", marginTop: 20, marginBottom: 10 }}>Date Wise Expense (Pie Chart)</Text>

      <VictoryPie
        data={dateData}
        x="expenseDate"
        y="amount"
        colorScale={["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#A28DFF"]}
        innerRadius={50}
      />
    </View>
  );
};

export default ExpenseCharts;
