require 'buildr/git_auto_version'

desc 'GWT DataType Library'
define 'gwt-datatypes' do
  project.group = 'org.realityforge.gwt.datatypes'
  compile.options.source = '1.7'
  compile.options.target = '1.7'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  pom.add_apache2_license
  pom.add_github_project("realityforge/gwt-datatypes")
  pom.add_developer('realityforge', "Peter Donald")
  pom.provided_dependencies.concat [:javax_annotation, :gwt_user, :gwt_dev]
  pom.optional_dependencies.concat [:jackson_core, :jackson_mapper]

  compile.with :javax_annotation, :gwt_user, :jackson_core, :jackson_mapper

  test.using :testng
  test.with :mockito

  package(:jar).include("#{_(:source, :main, :java)}/*")
  package(:sources)
  package(:javadoc)
end
