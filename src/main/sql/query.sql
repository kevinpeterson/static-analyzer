SELECT a.artifactId, a.groupId, a.versionId, avg(c.dit) avgDit,
(select count(*) from metrics.Dependency d
where a.artifactId = d.artifactId and a.groupId = d.groupId and a.versionId = d.versionId) as referenced
FROM metrics.Artifact a
inner join metrics.ClassMetrics c
on (a.id = c.artifact_id)
group by a.artifactId, a.groupId, a.versionId