import React, { Component } from 'react'
import ProjectTask from './ProjectTasks/ProjectTask'

class Backlog extends Component {
    render() {
        const { project_tasks } = this.props;

        const tasks = project_tasks.map(project => (
            <ProjectTask key={project.id} project_task={project} />
        ));

        let todoItems = [];

        let inProgressItems = [];

        let doneItems = [];

        for (var i = 0; i < tasks.length; i++) {
            console.log(tasks[i]);
        }

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-secondary text-white">
                                <h3>TO DO</h3>
                            </div>
                        </div>
                        {tasks}
                        {
                            // insert task here
                        }
                    </div>

                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-primary text-white">
                                <h3>In Progress</h3>
                            </div>
                        </div>
                    </div>

                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-success text-white">
                                <h3>Done</h3>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default Backlog;
