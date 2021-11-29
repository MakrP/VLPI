import React from "react";
import '../css/Statistic.css';

class Statistic extends React.Component 
{
    constructor(props)
    {
        super(props)
        this.state = {
            statistic: []
        }
    }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/statistic/admin');
        const data = await response.json();
        
        if (data.errorMessage !== undefined)
        {
            this.props.history.push({pathname:"/login", state: {errorMessage: data.errorMessage}})
        }

        this.setState({statistic: data})
    }

    onSortOrderChange = (event) => {
        let curr_stat = [...this.state.statistic]
        let order_elem = document.getElementById("OrderBy");
        let sort_elem = document.getElementById("SortBy");
        let order = order_elem.value;
        let sortby = sort_elem.value;

        if (order === 'Ascending')
        {
            switch(sortby)
            {
                case 'Total tasks':
                    curr_stat.sort((a,b) => {return a.totalTaskComplete - b.totalTaskComplete})
                    break;
                case 'Total time':
                    curr_stat.sort((a,b) => {return a.totalTimeOnTasks - b.totalTimeOnTasks})
                    break;
                case 'Average time':
                    curr_stat.sort((a,b) => {return a.averageTimeOnTasks - b.averageTimeOnTasks})
                    break;
                case 'Average mark':
                    curr_stat.sort((a,b) => {return a.averageMark - b.averageMark})
                    break;
                default:
                    break
            }
        }
        else
        {
            switch(sortby)
            {
                case 'Total tasks':
                    curr_stat.sort((a,b) => {return b.totalTaskComplete - a.totalTaskComplete})
                    break;
                case 'Total time':
                    curr_stat.sort((a,b) => {return b.totalTimeOnTasks - a.totalTimeOnTasks})
                    break;
                case 'Average time':
                    curr_stat.sort((a,b) => {return b.averageTimeOnTasks - a.averageTimeOnTasks})
                    break;
                case 'Average mark':
                    curr_stat.sort((a,b) => {return b.averageMark - a.averageMark})
                    break;
                default:
                    break
            }
        }

        this.setState({statistic: curr_stat})
    }

    render() {
        return (
            <div className="statistic-area-main">
                <div className="statistic-area-filter">
                    <div className="statistic-area-filter-sort">
                        <div>Sort By:</div>
                        <select onChange={this.onSortOrderChange} id="SortBy">
                            <option>Total tasks</option>
                            <option>Total time</option>
                            <option>Average time</option>
                            <option>Average mark</option>
                        </select>
                    </div>
                    <div className="statistic-area-filter-sort-order">
                        <div>Order By:</div>
                        <select onChange={this.onSortOrderChange} id="OrderBy">
                            <option>Ascending</option>
                            <option>Descending</option>
                        </select>
                    </div>
                </div>
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">UserId</th>
                            <th scope="col">Username</th>
                            <th scope="col">Total tasks</th>
                            <th scope="col">Total time</th>
                            <th scope="col">Average time</th>
                            <th scope="col">Average mark</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.statistic.map((value) => {
                                return (
                                    <tr>
                                        <td>{value.uuid}</td>
                                        <td>{value.username}</td>
                                        <td>{value.totalTaskComplete}</td>
                                        <td>{value.totalTimeOnTasks}</td>
                                        <td>{value.averageTimeOnTasks}</td>
                                        <td>{value.averageMark}</td>
                                    </tr> 
                                )
                            })
                        }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default Statistic;