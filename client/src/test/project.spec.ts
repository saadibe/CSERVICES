import { Project } from '../../app/common/entities/Project';

describe('Project Tests', () => {

  it('all_project_fields_are_null', () => {
    const project: Project = new Project();
    expect(project.getId()).toBeUndefined();
    expect(project.getName()).toBeUndefined();
    expect(project.getCode()).toBeUndefined();
  });

  it('set_id_changes_id', () => {
    const project: Project = new Project();
    project.setId(2);
    expect(project.getId()).toEqual(2);
  });

  it('set_name_changes_name', () => {
    const project: Project = new Project();
    project.setName('projectName');
    expect(project.getName()).toEqual('projectName');
  });

  it('set_code_changes_code', () => {
    const project: Project = new Project();
    project.setCode('code');
    expect(project.getCode()).toEqual('code');
  });
});
