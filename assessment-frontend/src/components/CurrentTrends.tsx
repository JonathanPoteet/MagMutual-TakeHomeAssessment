import React, { useEffect, useMemo, useState } from 'react';
import { RecentSignup, userService } from '../services/UserService';

export default function CurrentTrends() {
  const [trendData, setTrendData] = useState<RecentSignup[]>([]);

  useEffect(() => {
    const fetchTrends = async () => {
      try {
        const data = await userService.getTrends();
        setTrendData(data);
      } catch (error) {
        console.error('Error fetching trend data:', error);
      }
    };

    fetchTrends();
  }, []);

  const cityChartData = useMemo(() => {
    const cityCounts = new Map<string, number>();

    trendData.forEach((entry) => {
      if (!entry.city) return;
      cityCounts.set(entry.city, (cityCounts.get(entry.city) ?? 0) + 1);
    });

    return Array.from(cityCounts.entries())
      .map(([label, count]) => ({ label, count }))
      .sort((a, b) => b.count - a.count)
      .slice(0, 10);
  }, [trendData]);

  const professionChartData = useMemo(() => {
    const professionCounts = new Map<string, number>();

    trendData.forEach((entry) => {
      if (!entry.profession) return;
      professionCounts.set(entry.profession, (professionCounts.get(entry.profession) ?? 0) + 1);
    });

    return Array.from(professionCounts.entries())
      .map(([label, count]) => ({ label, count }))
      .sort((a, b) => b.count - a.count)
      .slice(0, 10);
  }, [trendData]);

  const dateChartData = useMemo(() => {
    const dateCounts = new Map<string, number>();

    trendData.forEach((entry) => {
      if (!entry.dateCreated) return;
      const dateLabel = new Date(entry.dateCreated).toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
      });

      dateCounts.set(dateLabel, (dateCounts.get(dateLabel) ?? 0) + 1);
    });

    return Array.from(dateCounts.entries())
      .map(([label, count]) => ({ label, count }))
      .sort((a, b) => new Date(a.label).getTime() - new Date(b.label).getTime());
  }, [trendData]);

  const renderChart = (data: { label: string; count: number }[], title: string) => {
    const maxCount = Math.max(1, ...data.map((item) => item.count));

    return (
      <div className="chart-panel">
        <h3>{title}</h3>
        <div className="bar-chart-wrapper">
          <div className="bar-chart">
            {data.map(({ label, count }) => (
              <div key={label} className="bar-group">
                <div className="bar-value">{count}</div>
                <div className="bar-column-wrap">
                  <div
                    className="bar-column"
                    style={{ height: `${(count / maxCount) * 100}%` }}
                    title={`${label}: ${count}`}
                  />
                </div>
                <div className="bar-label">{label}</div>
              </div>
            ))}
          </div>
        </div>
      </div>
    );
  };

  if (trendData.length === 0) {
    return (
      <section className="panel panel-trends">
        <div className="panel-header">
          <h2>Signup Trends</h2>
        </div>
        <p className="trend-empty-state">Loading recent signup data...</p>
      </section>
    );
  }

  return (
    <section className="panel panel-trends">
      <div className="panel-header">
        <h2>Recent Signups</h2>
      </div>
      <div className="trend-subtitle">Data based on the last 25 signups</div>
      <div className="multi-chart-layout">
        {renderChart(dateChartData, 'Recent Signup Dates')}
        {renderChart(cityChartData, 'City Breakdown')}
        {renderChart(professionChartData, 'Profession Breakdown')}
      </div>
    </section>
  );
}