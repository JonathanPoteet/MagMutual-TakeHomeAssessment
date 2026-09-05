import React from 'react';

const trendData = [
  { label: 'Chicago', value: '12 signups' },
  { label: 'Seattle', value: '9 signups' },
  { label: 'Austin', value: '7 signups' },
  { label: 'Boston', value: '5 signups' },
];

export default function CurrentTrends() {
  return (
    <section className="panel panel-trends">
      <div className="panel-header">
        <h2>Signup Trends</h2>
      </div>

      <div className="trend-list">
        {trendData.map((trend) => (
          <div key={trend.label} className="trend-item">
            <span>{trend.label}</span>
            <strong>{trend.value}</strong>
          </div>
        ))}
      </div>
    </section>
  );
}